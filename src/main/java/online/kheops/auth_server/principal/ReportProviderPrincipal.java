package online.kheops.auth_server.principal;

import online.kheops.auth_server.EntityManagerListener;
import online.kheops.auth_server.accesstoken.AccessToken.*;
import online.kheops.auth_server.capability.ScopeType;
import online.kheops.auth_server.entity.*;
import online.kheops.auth_server.report_provider.ClientIdNotFoundException;
import online.kheops.auth_server.series.SeriesNotFoundException;
import online.kheops.auth_server.user.AlbumUserPermissions;
import online.kheops.auth_server.util.KheopsLogBuilder;

import javax.persistence.EntityManager;
import javax.ws.rs.ForbiddenException;

import java.util.List;
import java.util.Optional;

import static online.kheops.auth_server.report_provider.ReportProviders.getReportProvider;
import static online.kheops.auth_server.series.Series.*;
import static online.kheops.auth_server.study.Studies.canAccessStudy;

public class ReportProviderPrincipal implements KheopsPrincipal {

    private EntityManager em;
    private final User user;
    private final boolean hasReadAccess;
    private final boolean hasWriteAccess;
    private final String actingParty;
    private final String capabilityTokenId;
    private final KheopsLogBuilder kheopsLogBuilder;

    private List<String> studyUids;
    private String clientId;
    private Album album;
    private final String originalToken;

    public ReportProviderPrincipal(User user, String actingParty, String capabilityTokenId, List<String> studyUids,
                                   String clientId, boolean hasReadAccessAccess, boolean hasWriteAccess, String originalToken) {
        try {
            album = getReportProvider(clientId).getAlbum();
        } catch (ClientIdNotFoundException e) {
            throw new ForbiddenException("Client id not found");
        }
        this.clientId = clientId;
        this.studyUids = studyUids;
        this.user = user;
        this.actingParty = actingParty;
        this.capabilityTokenId = capabilityTokenId;
        this.hasReadAccess = hasReadAccessAccess;
        this.hasWriteAccess = hasWriteAccess;
        this.originalToken = originalToken;

        kheopsLogBuilder = new KheopsLogBuilder()
                .provenance(this)
                .user(getUser().getKeycloakId())
                .clientID(clientId)
                .tokenType(TokenType.REPORT_PROVIDER_TOKEN);
    }

    @Override
    public String getName() { return user.getKeycloakId(); }

    @Override
    public boolean hasSeriesReadAccess(String studyInstanceUID, String seriesInstanceUID) {

        if (!hasReadAccess) {
            return false;
        }

        if (!studyUids.contains(studyInstanceUID)) {
            return false;
        }

        this.em = EntityManagerListener.createEntityManager();
        try {
            album = em.merge(album);
            return canAccessSeries(album, studyInstanceUID, seriesInstanceUID, em);

        } finally {
            em.close();
        }
    }

    @Override
    public boolean hasStudyReadAccess(String studyInstanceUID) {
        return hasStudyAccess(studyInstanceUID);
    }

    @Override
    public boolean hasUserAccess() { return false; }

    @Override
    public boolean hasSeriesWriteAccess(String studyInstanceUID, String seriesInstanceUID) {

        if (!hasWriteAccess) {
            return false;
        }

        if (!studyUids.contains(studyInstanceUID)) {
            return false;
        }

        this.em = EntityManagerListener.createEntityManager();
        try {

            if (!canAccessStudy(album, studyInstanceUID)) {
                return false;
            }

            //find if the series exist
            try {
                getSeries(studyInstanceUID, seriesInstanceUID, em);
            } catch (SeriesNotFoundException e) {
                return true;
            }

            // we need to check here if the series that was found is in the right album
            if(canAccessSeries(album, studyInstanceUID, seriesInstanceUID, em)) {
                return true;
            }
        } finally {
            em.close();
        }
        return false;
    }

    @Override
    public boolean hasStudyWriteAccess(String studyInstanceUID) {
        return hasStudyAccess(studyInstanceUID);
    }

    @Override
    public boolean hasAlbumPermission(AlbumUserPermissions usersPermission, String albumId) {
        this.em = EntityManagerListener.createEntityManager();
        try {
             album = em.merge(album);

             if(!album.getId().equals(albumId))  {
                 return false;
             }

            return usersPermission.hasProviderPermission(album);

        } finally {
            em.close();
        }
    }

    @Override
    public boolean hasAlbumAccess(String albumId){
        return albumId.equals(album.getId());
    }

    @Override
    public boolean hasInboxAccess() { return false; }

    @Override
    public User getUser() { return user; }

    @Override
    public ScopeType getScope() { return ScopeType.ALBUM; }

    @Override
    public String getAlbumID() { return album.getId(); }

    @Override
    public KheopsLogBuilder getKheopsLogBuilder() { return kheopsLogBuilder; }

    @Override
    public String toString() {
        return "[ReportProviderPrincipal user:" + getUser() + " albumId:" + album.getId() + " clientID:" + clientId + "]";
    }

    @Override
    public Optional<String> getClientId() {
        return Optional.of(clientId);
    }

    @Override
    public Optional<List<String>> getStudyList() {
        return Optional.of(studyUids);
    }

    @Override
    public Optional<String> getActingParty() {
        return Optional.ofNullable(actingParty);
    }

    @Override
    public Optional<String> getAuthorizedParty() {
        return Optional.of(clientId);
    }

    @Override
    public Optional<String> getCapabilityTokenId() {
        return Optional.ofNullable(capabilityTokenId);
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

    private boolean hasStudyAccess(String studyInstanceUID) {
        if (!studyUids.contains(studyInstanceUID)) {
            return false;
        }

        this.em = EntityManagerListener.createEntityManager();
        try {
            album = em.merge(album);
            return canAccessStudy(album, studyInstanceUID);
        } finally {
            em.close();
        }
    }
}
