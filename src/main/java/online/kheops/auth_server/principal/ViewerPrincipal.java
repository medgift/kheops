package online.kheops.auth_server.principal;

import online.kheops.auth_server.EntityManagerListener;
import online.kheops.auth_server.NotAlbumScopeTypeException;
import online.kheops.auth_server.accesstoken.ViewerAccessToken;
import online.kheops.auth_server.album.AlbumNotFoundException;
import online.kheops.auth_server.accesstoken.AccessToken;
import online.kheops.auth_server.capability.ScopeType;
import online.kheops.auth_server.entity.Album;
import online.kheops.auth_server.entity.Series;
import online.kheops.auth_server.entity.User;
import online.kheops.auth_server.series.SeriesNotFoundException;
import online.kheops.auth_server.user.UserNotFoundException;
import online.kheops.auth_server.user.AlbumUserPermissions;
import online.kheops.auth_server.util.KheopsLogBuilder;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static online.kheops.auth_server.album.Albums.*;
import static online.kheops.auth_server.series.Series.getSeries;
import static online.kheops.auth_server.series.SeriesQueries.findSeriesListByStudyUIDFromAlbum;
import static online.kheops.auth_server.series.SeriesQueries.findSeriesListByStudyUIDFromInbox;
import static online.kheops.auth_server.user.Users.getOrCreateUser;

public class ViewerPrincipal implements KheopsPrincipal {

    private EntityManager em;
    private final ViewerAccessToken viewerAccessToken;
    private final KheopsPrincipal kheopsPrincipal;
    private final String originalToken;
    private final KheopsLogBuilder kheopsLogBuilder;

    public ViewerPrincipal(ServletContext servletContext, ViewerAccessToken viewerAccessToken, String originalToken) {

        final AccessToken accessToken = viewerAccessToken.getAccessToken();

        final User user;
        try {
            user = getOrCreateUser(accessToken.getSubject());
        } catch (UserNotFoundException e) {
            throw new IllegalStateException(e);
        }
        kheopsPrincipal = accessToken.newPrincipal(servletContext, user);

        this.viewerAccessToken = viewerAccessToken;
        this.originalToken = originalToken;

        kheopsLogBuilder = new KheopsLogBuilder()
                .provenance(this)
                .user(getUser().getKeycloakId())
                .tokenType(AccessToken.TokenType.VIEWER_TOKEN);
    }

    @Override
    public String getName() { return kheopsPrincipal.getName(); }

    @Override
    public boolean hasSeriesReadAccess(String studyInstanceUID, String seriesInstanceUID) {

        if(!kheopsPrincipal.hasSeriesReadAccess(studyInstanceUID, seriesInstanceUID)) {
            return false;
        }

        if (!viewerAccessToken.isInbox() && viewerAccessToken.getSourceId() == null) {
            return studyInstanceUID.equals(viewerAccessToken.getStudyInstanceUID());
        } else {
            this.em = EntityManagerListener.createEntityManager();
            try {
                final List<Series> seriesList;
                if (viewerAccessToken.isInbox()) {
                    seriesList = findSeriesListByStudyUIDFromInbox(getUser(), studyInstanceUID, em);
                } else {
                    final Album album = getAlbum(viewerAccessToken.getSourceId(), em);
                    seriesList = findSeriesListByStudyUIDFromAlbum(album, studyInstanceUID, em);
                }

                final Series series = getSeries(studyInstanceUID, seriesInstanceUID, em);
                return seriesList.contains(series);
            } catch (AlbumNotFoundException | SeriesNotFoundException e) {
                return false;
            } finally {
                em.close();
            }
        }
    }

    @Override
    public boolean hasStudyReadAccess(String studyInstanceUID) {
        if(!kheopsPrincipal.hasStudyReadAccess(studyInstanceUID)) {
            return false;
        }

        return studyInstanceUID.equals(viewerAccessToken.getStudyInstanceUID());
    }

    @Override
    public boolean hasUserAccess() { return false; }

    @Override
    public boolean hasSeriesWriteAccess(String studyInstanceUID, String seriesInstanceUID) { return false; }

    @Override
    public boolean hasStudyWriteAccess(String study) { return false; }

    @Override
    public boolean hasAlbumPermission(AlbumUserPermissions usersPermission, String albumId) {

        if (!kheopsPrincipal.hasAlbumPermission(usersPermission, albumId)) {
            return false;
        } else {
            this.em = EntityManagerListener.createEntityManager();
            try {
                final User userMerge = em.merge(getUser());
                final Album album;
                try {
                    album = getAlbum(albumId, em);
                } catch (AlbumNotFoundException e) {
                    return false;
                }

                if(!isMemberOfAlbum(userMerge, album, em)) {
                    return false;
                }

                if(userMerge.getInbox() == album) {
                    return false;
                }

                return usersPermission.hasViewerPermission(album);
            } finally {
                em.close();
            }
        }
    }

    @Override
    public boolean hasAlbumAccess(String albumId) {

        return kheopsPrincipal.hasAlbumAccess(albumId) && !viewerAccessToken.isInbox() &&
                (viewerAccessToken.getSourceId() == null || viewerAccessToken.getSourceId().equals(albumId));
    }

    @Override
    public boolean hasInboxAccess() {
        return viewerAccessToken.isInbox();
    }

    @Override
    public User getUser() { return kheopsPrincipal.getUser(); }

    @Override
    public ScopeType getScope() {
        if(!viewerAccessToken.isInbox() || kheopsPrincipal.getScope() == ScopeType.ALBUM) {
            return ScopeType.ALBUM;
        } else {
            return ScopeType.USER;
        }

    }

    @Override
    public String getAlbumID() throws NotAlbumScopeTypeException, AlbumNotFoundException {
        final String albumID;
        if (!viewerAccessToken.isInbox() && viewerAccessToken.getSourceId() == null) {
           albumID = kheopsPrincipal.getAlbumID();
        } else if (!viewerAccessToken.isInbox()) {
            albumID = viewerAccessToken.getSourceId();
        } else {
            throw new NotAlbumScopeTypeException("");
        }

        if(kheopsPrincipal.hasAlbumAccess(albumID)) {
            return albumID;
        } else {
            throw new AlbumNotFoundException("");
        }
    }

    @Override
    public Optional<String> getActingParty() {
        return kheopsPrincipal.getActingParty();
    }

    @Override
    public Optional<String> getAuthorizedParty() {
        return kheopsPrincipal.getAuthorizedParty();
    }

    @Override
    public Optional<String> getCapabilityTokenId() {
        return kheopsPrincipal.getCapabilityTokenId();
    }

    @Override
    public KheopsLogBuilder getKheopsLogBuilder() {
        return kheopsLogBuilder;
    }

    @Override
    public String toString() {
        return "[ViewerPrincipal user:" + getUser() + " scope:" + getScope() + " hasUserAccess:" + hasUserAccess() + " hasInboxAccess:" + hasInboxAccess() + "]";
    }

    @Override
    public Optional<List<String>> getStudyList() {
        return Optional.of(Collections.singletonList(viewerAccessToken.getStudyInstanceUID()));
    }

    private boolean linkAuthorization;
    @Override
    public void setLink(boolean linkAuthorization) {
        this.linkAuthorization = linkAuthorization;
    }

    @Override
    public boolean isLink() { return linkAuthorization;  }

    @Override
    public String getOriginalToken() {
        return originalToken;
    }
}
