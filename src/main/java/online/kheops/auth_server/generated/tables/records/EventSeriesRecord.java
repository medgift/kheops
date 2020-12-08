/*
 * This file is generated by jOOQ.
 */
package online.kheops.auth_server.generated.tables.records;


import online.kheops.auth_server.generated.tables.EventSeries;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventSeriesRecord extends UpdatableRecordImpl<EventSeriesRecord> implements Record3<Long, Long, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.event_series.pk</code>.
     */
    public void setPk(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.event_series.pk</code>.
     */
    public Long getPk() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.event_series.event_fk</code>.
     */
    public void setEventFk(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.event_series.event_fk</code>.
     */
    public Long getEventFk() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.event_series.series_fk</code>.
     */
    public void setSeriesFk(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.event_series.series_fk</code>.
     */
    public Long getSeriesFk() {
        return (Long) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, Long, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, Long, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return EventSeries.EVENT_SERIES.PK;
    }

    @Override
    public Field<Long> field2() {
        return EventSeries.EVENT_SERIES.EVENT_FK;
    }

    @Override
    public Field<Long> field3() {
        return EventSeries.EVENT_SERIES.SERIES_FK;
    }

    @Override
    public Long component1() {
        return getPk();
    }

    @Override
    public Long component2() {
        return getEventFk();
    }

    @Override
    public Long component3() {
        return getSeriesFk();
    }

    @Override
    public Long value1() {
        return getPk();
    }

    @Override
    public Long value2() {
        return getEventFk();
    }

    @Override
    public Long value3() {
        return getSeriesFk();
    }

    @Override
    public EventSeriesRecord value1(Long value) {
        setPk(value);
        return this;
    }

    @Override
    public EventSeriesRecord value2(Long value) {
        setEventFk(value);
        return this;
    }

    @Override
    public EventSeriesRecord value3(Long value) {
        setSeriesFk(value);
        return this;
    }

    @Override
    public EventSeriesRecord values(Long value1, Long value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EventSeriesRecord
     */
    public EventSeriesRecord() {
        super(EventSeries.EVENT_SERIES);
    }

    /**
     * Create a detached, initialised EventSeriesRecord
     */
    public EventSeriesRecord(Long pk, Long eventFk, Long seriesFk) {
        super(EventSeries.EVENT_SERIES);

        setPk(pk);
        setEventFk(eventFk);
        setSeriesFk(seriesFk);
    }
}
