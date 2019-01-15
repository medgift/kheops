/*
 * This file is generated by jOOQ.
 */
package online.kheops.auth_server.generated;


import javax.annotation.Generated;

import online.kheops.auth_server.generated.tables.AlbumSeries;
import online.kheops.auth_server.generated.tables.AlbumUser;
import online.kheops.auth_server.generated.tables.Albums;
import online.kheops.auth_server.generated.tables.Capabilities;
import online.kheops.auth_server.generated.tables.Events;
import online.kheops.auth_server.generated.tables.Series;
import online.kheops.auth_server.generated.tables.Studies;
import online.kheops.auth_server.generated.tables.Users;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ALBUM_SERIES_PK = Indexes0.ALBUM_SERIES_PK;
    public static final Index ALBUM_SERIES_UNIQUE = Indexes0.ALBUM_SERIES_UNIQUE;
    public static final Index ALBUM_USER_PK = Indexes0.ALBUM_USER_PK;
    public static final Index ALBUM_USER_UNIQUE = Indexes0.ALBUM_USER_UNIQUE;
    public static final Index ALBUM_PK = Indexes0.ALBUM_PK;
    public static final Index ALBUMS_ID_UNIQUE = Indexes0.ALBUMS_ID_UNIQUE;
    public static final Index CAPABILITIES_ID_UNIQUE = Indexes0.CAPABILITIES_ID_UNIQUE;
    public static final Index CAPABILITIES_PK = Indexes0.CAPABILITIES_PK;
    public static final Index CAPABILITIES_SECRET_INDEX = Indexes0.CAPABILITIES_SECRET_INDEX;
    public static final Index CAPABILITIES_SECRET_UNIQUE = Indexes0.CAPABILITIES_SECRET_UNIQUE;
    public static final Index CAPABILITIES_USER_FK_INDEX = Indexes0.CAPABILITIES_USER_FK_INDEX;
    public static final Index EVENT_PK = Indexes0.EVENT_PK;
    public static final Index SERIES_PK = Indexes0.SERIES_PK;
    public static final Index SERIES_POPULATED_INDEX = Indexes0.SERIES_POPULATED_INDEX;
    public static final Index SERIES_UID_INDEX = Indexes0.SERIES_UID_INDEX;
    public static final Index SERIES_UID_UNIQUE = Indexes0.SERIES_UID_UNIQUE;
    public static final Index STUDY_FK_INDEX = Indexes0.STUDY_FK_INDEX;
    public static final Index ACCESSION_NUMBER_INDEX = Indexes0.ACCESSION_NUMBER_INDEX;
    public static final Index PATIENT_ID_INDEX = Indexes0.PATIENT_ID_INDEX;
    public static final Index POPULATED_INDEX = Indexes0.POPULATED_INDEX;
    public static final Index STUDIES_PK = Indexes0.STUDIES_PK;
    public static final Index STUDY_DATE_INDEX = Indexes0.STUDY_DATE_INDEX;
    public static final Index STUDY_ID_INDEX = Indexes0.STUDY_ID_INDEX;
    public static final Index STUDY_TIME_INDEX = Indexes0.STUDY_TIME_INDEX;
    public static final Index STUDY_UID_INDEX = Indexes0.STUDY_UID_INDEX;
    public static final Index STUDY_UID_UNIQUE = Indexes0.STUDY_UID_UNIQUE;
    public static final Index KEYCLOAK_ID_UNIQUE = Indexes0.KEYCLOAK_ID_UNIQUE;
    public static final Index USERS_PK = Indexes0.USERS_PK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index ALBUM_SERIES_PK = Internal.createIndex("album_series_pk", AlbumSeries.ALBUM_SERIES, new OrderField[] { AlbumSeries.ALBUM_SERIES.PK }, true);
        public static Index ALBUM_SERIES_UNIQUE = Internal.createIndex("album_series_unique", AlbumSeries.ALBUM_SERIES, new OrderField[] { AlbumSeries.ALBUM_SERIES.ALBUM_FK, AlbumSeries.ALBUM_SERIES.SERIES_FK }, true);
        public static Index ALBUM_USER_PK = Internal.createIndex("album_user_pk", AlbumUser.ALBUM_USER, new OrderField[] { AlbumUser.ALBUM_USER.PK }, true);
        public static Index ALBUM_USER_UNIQUE = Internal.createIndex("album_user_unique", AlbumUser.ALBUM_USER, new OrderField[] { AlbumUser.ALBUM_USER.ALBUM_FK, AlbumUser.ALBUM_USER.USER_FK }, true);
        public static Index ALBUM_PK = Internal.createIndex("album_pk", Albums.ALBUMS, new OrderField[] { Albums.ALBUMS.PK }, true);
        public static Index ALBUMS_ID_UNIQUE = Internal.createIndex("albums_id_unique", Albums.ALBUMS, new OrderField[] { Albums.ALBUMS.ID }, true);
        public static Index CAPABILITIES_ID_UNIQUE = Internal.createIndex("capabilities_id_unique", Capabilities.CAPABILITIES, new OrderField[] { Capabilities.CAPABILITIES.ID }, true);
        public static Index CAPABILITIES_PK = Internal.createIndex("capabilities_pk", Capabilities.CAPABILITIES, new OrderField[] { Capabilities.CAPABILITIES.PK }, true);
        public static Index CAPABILITIES_SECRET_INDEX = Internal.createIndex("capabilities_secret_index", Capabilities.CAPABILITIES, new OrderField[] { Capabilities.CAPABILITIES.SECRET }, false);
        public static Index CAPABILITIES_SECRET_UNIQUE = Internal.createIndex("capabilities_secret_unique", Capabilities.CAPABILITIES, new OrderField[] { Capabilities.CAPABILITIES.SECRET }, true);
        public static Index CAPABILITIES_USER_FK_INDEX = Internal.createIndex("capabilities_user_fk_index", Capabilities.CAPABILITIES, new OrderField[] { Capabilities.CAPABILITIES.USER_FK }, false);
        public static Index EVENT_PK = Internal.createIndex("event_pk", Events.EVENTS, new OrderField[] { Events.EVENTS.PK }, true);
        public static Index SERIES_PK = Internal.createIndex("series_pk", Series.SERIES, new OrderField[] { Series.SERIES.PK }, true);
        public static Index SERIES_POPULATED_INDEX = Internal.createIndex("series_populated_index", Series.SERIES, new OrderField[] { Series.SERIES.POPULATED }, false);
        public static Index SERIES_UID_INDEX = Internal.createIndex("series_uid_index", Series.SERIES, new OrderField[] { Series.SERIES.SERIES_UID }, false);
        public static Index SERIES_UID_UNIQUE = Internal.createIndex("series_uid_unique", Series.SERIES, new OrderField[] { Series.SERIES.SERIES_UID }, true);
        public static Index STUDY_FK_INDEX = Internal.createIndex("study_fk_index", Series.SERIES, new OrderField[] { Series.SERIES.STUDY_FK }, false);
        public static Index ACCESSION_NUMBER_INDEX = Internal.createIndex("accession_number_index", Studies.STUDIES, new OrderField[] { Studies.STUDIES.ACCESSION_NUMBER }, false);
        public static Index PATIENT_ID_INDEX = Internal.createIndex("patient_id_index", Studies.STUDIES, new OrderField[] { Studies.STUDIES.PATIENT_ID }, false);
        public static Index POPULATED_INDEX = Internal.createIndex("populated_index", Studies.STUDIES, new OrderField[] { Studies.STUDIES.POPULATED }, false);
        public static Index STUDIES_PK = Internal.createIndex("studies_pk", Studies.STUDIES, new OrderField[] { Studies.STUDIES.PK }, true);
        public static Index STUDY_DATE_INDEX = Internal.createIndex("study_date_index", Studies.STUDIES, new OrderField[] { Studies.STUDIES.STUDY_DATE }, false);
        public static Index STUDY_ID_INDEX = Internal.createIndex("study_id_index", Studies.STUDIES, new OrderField[] { Studies.STUDIES.STUDY_ID }, false);
        public static Index STUDY_TIME_INDEX = Internal.createIndex("study_time_index", Studies.STUDIES, new OrderField[] { Studies.STUDIES.STUDY_TIME }, false);
        public static Index STUDY_UID_INDEX = Internal.createIndex("study_uid_index", Studies.STUDIES, new OrderField[] { Studies.STUDIES.STUDY_UID }, false);
        public static Index STUDY_UID_UNIQUE = Internal.createIndex("study_uid_unique", Studies.STUDIES, new OrderField[] { Studies.STUDIES.STUDY_UID }, true);
        public static Index KEYCLOAK_ID_UNIQUE = Internal.createIndex("keycloak_id_unique", Users.USERS, new OrderField[] { Users.USERS.KEYCLOAK_ID }, true);
        public static Index USERS_PK = Internal.createIndex("users_pk", Users.USERS, new OrderField[] { Users.USERS.PK }, true);
    }
}
