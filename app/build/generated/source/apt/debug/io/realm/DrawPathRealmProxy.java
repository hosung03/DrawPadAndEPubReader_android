package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.OsObject;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DrawPathRealmProxy extends com.hosung.drawpadandepubreader.models.DrawPath
    implements RealmObjectProxy, DrawPathRealmProxyInterface {

    static final class DrawPathColumnInfo extends ColumnInfo {
        long savedIndex;
        long completedIndex;
        long colorIndex;
        long bushsizeIndex;
        long pointsIndex;

        DrawPathColumnInfo(SharedRealm realm, Table table) {
            super(5);
            this.savedIndex = addColumnDetails(table, "saved", RealmFieldType.BOOLEAN);
            this.completedIndex = addColumnDetails(table, "completed", RealmFieldType.BOOLEAN);
            this.colorIndex = addColumnDetails(table, "color", RealmFieldType.STRING);
            this.bushsizeIndex = addColumnDetails(table, "bushsize", RealmFieldType.INTEGER);
            this.pointsIndex = addColumnDetails(table, "points", RealmFieldType.LIST);
        }

        DrawPathColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new DrawPathColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final DrawPathColumnInfo src = (DrawPathColumnInfo) rawSrc;
            final DrawPathColumnInfo dst = (DrawPathColumnInfo) rawDst;
            dst.savedIndex = src.savedIndex;
            dst.completedIndex = src.completedIndex;
            dst.colorIndex = src.colorIndex;
            dst.bushsizeIndex = src.bushsizeIndex;
            dst.pointsIndex = src.pointsIndex;
        }
    }

    private DrawPathColumnInfo columnInfo;
    private ProxyState<com.hosung.drawpadandepubreader.models.DrawPath> proxyState;
    private RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> pointsRealmList;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("saved");
        fieldNames.add("completed");
        fieldNames.add("color");
        fieldNames.add("bushsize");
        fieldNames.add("points");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    DrawPathRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (DrawPathColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.hosung.drawpadandepubreader.models.DrawPath>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$saved() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.savedIndex);
    }

    @Override
    public void realmSet$saved(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.savedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.savedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$completed() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.completedIndex);
    }

    @Override
    public void realmSet$completed(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.completedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.completedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$color() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.colorIndex);
    }

    @Override
    public void realmSet$color(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.colorIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.colorIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.colorIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.colorIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$bushsize() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.bushsizeIndex)) {
            return null;
        }
        return (int) proxyState.getRow$realm().getLong(columnInfo.bushsizeIndex);
    }

    @Override
    public void realmSet$bushsize(Integer value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.bushsizeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(columnInfo.bushsizeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.bushsizeIndex);
            return;
        }
        proxyState.getRow$realm().setLong(columnInfo.bushsizeIndex, value);
    }

    @Override
    public RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> realmGet$points() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (pointsRealmList != null) {
            return pointsRealmList;
        } else {
            LinkView linkView = proxyState.getRow$realm().getLinkList(columnInfo.pointsIndex);
            pointsRealmList = new RealmList<com.hosung.drawpadandepubreader.models.DrawPoint>(com.hosung.drawpadandepubreader.models.DrawPoint.class, linkView, proxyState.getRealm$realm());
            return pointsRealmList;
        }
    }

    @Override
    public void realmSet$points(RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("points")) {
                return;
            }
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> original = value;
                value = new RealmList<com.hosung.drawpadandepubreader.models.DrawPoint>();
                for (com.hosung.drawpadandepubreader.models.DrawPoint item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        LinkView links = proxyState.getRow$realm().getLinkList(columnInfo.pointsIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmModel linkedObject : (RealmList<? extends RealmModel>) value) {
            if (!(RealmObject.isManaged(linkedObject) && RealmObject.isValid(linkedObject))) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (((RealmObjectProxy)linkedObject).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            links.add(((RealmObjectProxy)linkedObject).realmGet$proxyState().getRow$realm().getIndex());
        }
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("DrawPath")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("DrawPath");
            realmObjectSchema.add("saved", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("completed", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("color", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("bushsize", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            if (!realmSchema.contains("DrawPoint")) {
                DrawPointRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add("points", RealmFieldType.LIST, realmSchema.get("DrawPoint"));
            return realmObjectSchema;
        }
        return realmSchema.get("DrawPath");
    }

    public static DrawPathColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_DrawPath")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'DrawPath' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_DrawPath");
        final long columnCount = table.getColumnCount();
        if (columnCount != 5) {
            if (columnCount < 5) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 5 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 5 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 5 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final DrawPathColumnInfo columnInfo = new DrawPathColumnInfo(sharedRealm, table);

        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }

        if (!columnTypes.containsKey("saved")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'saved' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("saved") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'saved' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.savedIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'saved' does support null values in the existing Realm file. Use corresponding boxed type for field 'saved' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("completed")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'completed' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("completed") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'completed' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.completedIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'completed' does support null values in the existing Realm file. Use corresponding boxed type for field 'completed' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("color")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'color' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("color") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'color' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.colorIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'color' is required. Either set @Required to field 'color' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("bushsize")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'bushsize' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("bushsize") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Integer' for field 'bushsize' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.bushsizeIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(),"Field 'bushsize' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'bushsize' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("points")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'points'");
        }
        if (columnTypes.get("points") != RealmFieldType.LIST) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'DrawPoint' for field 'points'");
        }
        if (!sharedRealm.hasTable("class_DrawPoint")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_DrawPoint' for field 'points'");
        }
        Table table_4 = sharedRealm.getTable("class_DrawPoint");
        if (!table.getLinkTarget(columnInfo.pointsIndex).hasSameSchema(table_4)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'points': '" + table.getLinkTarget(columnInfo.pointsIndex).getName() + "' expected - was '" + table_4.getName() + "'");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_DrawPath";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.hosung.drawpadandepubreader.models.DrawPath createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        if (json.has("points")) {
            excludeFields.add("points");
        }
        com.hosung.drawpadandepubreader.models.DrawPath obj = realm.createObjectInternal(com.hosung.drawpadandepubreader.models.DrawPath.class, true, excludeFields);
        if (json.has("saved")) {
            if (json.isNull("saved")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'saved' to null.");
            } else {
                ((DrawPathRealmProxyInterface) obj).realmSet$saved((boolean) json.getBoolean("saved"));
            }
        }
        if (json.has("completed")) {
            if (json.isNull("completed")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'completed' to null.");
            } else {
                ((DrawPathRealmProxyInterface) obj).realmSet$completed((boolean) json.getBoolean("completed"));
            }
        }
        if (json.has("color")) {
            if (json.isNull("color")) {
                ((DrawPathRealmProxyInterface) obj).realmSet$color(null);
            } else {
                ((DrawPathRealmProxyInterface) obj).realmSet$color((String) json.getString("color"));
            }
        }
        if (json.has("bushsize")) {
            if (json.isNull("bushsize")) {
                ((DrawPathRealmProxyInterface) obj).realmSet$bushsize(null);
            } else {
                ((DrawPathRealmProxyInterface) obj).realmSet$bushsize((int) json.getInt("bushsize"));
            }
        }
        if (json.has("points")) {
            if (json.isNull("points")) {
                ((DrawPathRealmProxyInterface) obj).realmSet$points(null);
            } else {
                ((DrawPathRealmProxyInterface) obj).realmGet$points().clear();
                JSONArray array = json.getJSONArray("points");
                for (int i = 0; i < array.length(); i++) {
                    com.hosung.drawpadandepubreader.models.DrawPoint item = DrawPointRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    ((DrawPathRealmProxyInterface) obj).realmGet$points().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.hosung.drawpadandepubreader.models.DrawPath createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.hosung.drawpadandepubreader.models.DrawPath obj = new com.hosung.drawpadandepubreader.models.DrawPath();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("saved")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'saved' to null.");
                } else {
                    ((DrawPathRealmProxyInterface) obj).realmSet$saved((boolean) reader.nextBoolean());
                }
            } else if (name.equals("completed")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'completed' to null.");
                } else {
                    ((DrawPathRealmProxyInterface) obj).realmSet$completed((boolean) reader.nextBoolean());
                }
            } else if (name.equals("color")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((DrawPathRealmProxyInterface) obj).realmSet$color(null);
                } else {
                    ((DrawPathRealmProxyInterface) obj).realmSet$color((String) reader.nextString());
                }
            } else if (name.equals("bushsize")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((DrawPathRealmProxyInterface) obj).realmSet$bushsize(null);
                } else {
                    ((DrawPathRealmProxyInterface) obj).realmSet$bushsize((int) reader.nextInt());
                }
            } else if (name.equals("points")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((DrawPathRealmProxyInterface) obj).realmSet$points(null);
                } else {
                    ((DrawPathRealmProxyInterface) obj).realmSet$points(new RealmList<com.hosung.drawpadandepubreader.models.DrawPoint>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.hosung.drawpadandepubreader.models.DrawPoint item = DrawPointRealmProxy.createUsingJsonStream(realm, reader);
                        ((DrawPathRealmProxyInterface) obj).realmGet$points().add(item);
                    }
                    reader.endArray();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.hosung.drawpadandepubreader.models.DrawPath copyOrUpdate(Realm realm, com.hosung.drawpadandepubreader.models.DrawPath object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.hosung.drawpadandepubreader.models.DrawPath) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.hosung.drawpadandepubreader.models.DrawPath copy(Realm realm, com.hosung.drawpadandepubreader.models.DrawPath newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.hosung.drawpadandepubreader.models.DrawPath) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.hosung.drawpadandepubreader.models.DrawPath realmObject = realm.createObjectInternal(com.hosung.drawpadandepubreader.models.DrawPath.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((DrawPathRealmProxyInterface) realmObject).realmSet$saved(((DrawPathRealmProxyInterface) newObject).realmGet$saved());
            ((DrawPathRealmProxyInterface) realmObject).realmSet$completed(((DrawPathRealmProxyInterface) newObject).realmGet$completed());
            ((DrawPathRealmProxyInterface) realmObject).realmSet$color(((DrawPathRealmProxyInterface) newObject).realmGet$color());
            ((DrawPathRealmProxyInterface) realmObject).realmSet$bushsize(((DrawPathRealmProxyInterface) newObject).realmGet$bushsize());

            RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> pointsList = ((DrawPathRealmProxyInterface) newObject).realmGet$points();
            if (pointsList != null) {
                RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> pointsRealmList = ((DrawPathRealmProxyInterface) realmObject).realmGet$points();
                for (int i = 0; i < pointsList.size(); i++) {
                    com.hosung.drawpadandepubreader.models.DrawPoint pointsItem = pointsList.get(i);
                    com.hosung.drawpadandepubreader.models.DrawPoint cachepoints = (com.hosung.drawpadandepubreader.models.DrawPoint) cache.get(pointsItem);
                    if (cachepoints != null) {
                        pointsRealmList.add(cachepoints);
                    } else {
                        pointsRealmList.add(DrawPointRealmProxy.copyOrUpdate(realm, pointsList.get(i), update, cache));
                    }
                }
            }

            return realmObject;
        }
    }

    public static long insert(Realm realm, com.hosung.drawpadandepubreader.models.DrawPath object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawPath.class);
        long tableNativePtr = table.getNativePtr();
        DrawPathColumnInfo columnInfo = (DrawPathColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawPath.class);
        long rowIndex = OsObject.createRow(realm.sharedRealm, table);
        cache.put(object, rowIndex);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.savedIndex, rowIndex, ((DrawPathRealmProxyInterface)object).realmGet$saved(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.completedIndex, rowIndex, ((DrawPathRealmProxyInterface)object).realmGet$completed(), false);
        String realmGet$color = ((DrawPathRealmProxyInterface)object).realmGet$color();
        if (realmGet$color != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.colorIndex, rowIndex, realmGet$color, false);
        }
        Number realmGet$bushsize = ((DrawPathRealmProxyInterface)object).realmGet$bushsize();
        if (realmGet$bushsize != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.bushsizeIndex, rowIndex, realmGet$bushsize.longValue(), false);
        }

        RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> pointsList = ((DrawPathRealmProxyInterface) object).realmGet$points();
        if (pointsList != null) {
            long pointsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.pointsIndex, rowIndex);
            for (com.hosung.drawpadandepubreader.models.DrawPoint pointsItem : pointsList) {
                Long cacheItemIndexpoints = cache.get(pointsItem);
                if (cacheItemIndexpoints == null) {
                    cacheItemIndexpoints = DrawPointRealmProxy.insert(realm, pointsItem, cache);
                }
                LinkView.nativeAdd(pointsNativeLinkViewPtr, cacheItemIndexpoints);
            }
        }

        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawPath.class);
        long tableNativePtr = table.getNativePtr();
        DrawPathColumnInfo columnInfo = (DrawPathColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawPath.class);
        com.hosung.drawpadandepubreader.models.DrawPath object = null;
        while (objects.hasNext()) {
            object = (com.hosung.drawpadandepubreader.models.DrawPath) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = OsObject.createRow(realm.sharedRealm, table);
                cache.put(object, rowIndex);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.savedIndex, rowIndex, ((DrawPathRealmProxyInterface)object).realmGet$saved(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.completedIndex, rowIndex, ((DrawPathRealmProxyInterface)object).realmGet$completed(), false);
                String realmGet$color = ((DrawPathRealmProxyInterface)object).realmGet$color();
                if (realmGet$color != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.colorIndex, rowIndex, realmGet$color, false);
                }
                Number realmGet$bushsize = ((DrawPathRealmProxyInterface)object).realmGet$bushsize();
                if (realmGet$bushsize != null) {
                    Table.nativeSetLong(tableNativePtr, columnInfo.bushsizeIndex, rowIndex, realmGet$bushsize.longValue(), false);
                }

                RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> pointsList = ((DrawPathRealmProxyInterface) object).realmGet$points();
                if (pointsList != null) {
                    long pointsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.pointsIndex, rowIndex);
                    for (com.hosung.drawpadandepubreader.models.DrawPoint pointsItem : pointsList) {
                        Long cacheItemIndexpoints = cache.get(pointsItem);
                        if (cacheItemIndexpoints == null) {
                            cacheItemIndexpoints = DrawPointRealmProxy.insert(realm, pointsItem, cache);
                        }
                        LinkView.nativeAdd(pointsNativeLinkViewPtr, cacheItemIndexpoints);
                    }
                }

            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.hosung.drawpadandepubreader.models.DrawPath object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawPath.class);
        long tableNativePtr = table.getNativePtr();
        DrawPathColumnInfo columnInfo = (DrawPathColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawPath.class);
        long rowIndex = OsObject.createRow(realm.sharedRealm, table);
        cache.put(object, rowIndex);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.savedIndex, rowIndex, ((DrawPathRealmProxyInterface)object).realmGet$saved(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.completedIndex, rowIndex, ((DrawPathRealmProxyInterface)object).realmGet$completed(), false);
        String realmGet$color = ((DrawPathRealmProxyInterface)object).realmGet$color();
        if (realmGet$color != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.colorIndex, rowIndex, realmGet$color, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.colorIndex, rowIndex, false);
        }
        Number realmGet$bushsize = ((DrawPathRealmProxyInterface)object).realmGet$bushsize();
        if (realmGet$bushsize != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.bushsizeIndex, rowIndex, realmGet$bushsize.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.bushsizeIndex, rowIndex, false);
        }

        long pointsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.pointsIndex, rowIndex);
        LinkView.nativeClear(pointsNativeLinkViewPtr);
        RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> pointsList = ((DrawPathRealmProxyInterface) object).realmGet$points();
        if (pointsList != null) {
            for (com.hosung.drawpadandepubreader.models.DrawPoint pointsItem : pointsList) {
                Long cacheItemIndexpoints = cache.get(pointsItem);
                if (cacheItemIndexpoints == null) {
                    cacheItemIndexpoints = DrawPointRealmProxy.insertOrUpdate(realm, pointsItem, cache);
                }
                LinkView.nativeAdd(pointsNativeLinkViewPtr, cacheItemIndexpoints);
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawPath.class);
        long tableNativePtr = table.getNativePtr();
        DrawPathColumnInfo columnInfo = (DrawPathColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawPath.class);
        com.hosung.drawpadandepubreader.models.DrawPath object = null;
        while (objects.hasNext()) {
            object = (com.hosung.drawpadandepubreader.models.DrawPath) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = OsObject.createRow(realm.sharedRealm, table);
                cache.put(object, rowIndex);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.savedIndex, rowIndex, ((DrawPathRealmProxyInterface)object).realmGet$saved(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.completedIndex, rowIndex, ((DrawPathRealmProxyInterface)object).realmGet$completed(), false);
                String realmGet$color = ((DrawPathRealmProxyInterface)object).realmGet$color();
                if (realmGet$color != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.colorIndex, rowIndex, realmGet$color, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.colorIndex, rowIndex, false);
                }
                Number realmGet$bushsize = ((DrawPathRealmProxyInterface)object).realmGet$bushsize();
                if (realmGet$bushsize != null) {
                    Table.nativeSetLong(tableNativePtr, columnInfo.bushsizeIndex, rowIndex, realmGet$bushsize.longValue(), false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.bushsizeIndex, rowIndex, false);
                }

                long pointsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.pointsIndex, rowIndex);
                LinkView.nativeClear(pointsNativeLinkViewPtr);
                RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> pointsList = ((DrawPathRealmProxyInterface) object).realmGet$points();
                if (pointsList != null) {
                    for (com.hosung.drawpadandepubreader.models.DrawPoint pointsItem : pointsList) {
                        Long cacheItemIndexpoints = cache.get(pointsItem);
                        if (cacheItemIndexpoints == null) {
                            cacheItemIndexpoints = DrawPointRealmProxy.insertOrUpdate(realm, pointsItem, cache);
                        }
                        LinkView.nativeAdd(pointsNativeLinkViewPtr, cacheItemIndexpoints);
                    }
                }

            }
        }
    }

    public static com.hosung.drawpadandepubreader.models.DrawPath createDetachedCopy(com.hosung.drawpadandepubreader.models.DrawPath realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.hosung.drawpadandepubreader.models.DrawPath unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.hosung.drawpadandepubreader.models.DrawPath)cachedObject.object;
            } else {
                unmanagedObject = (com.hosung.drawpadandepubreader.models.DrawPath)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.hosung.drawpadandepubreader.models.DrawPath();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((DrawPathRealmProxyInterface) unmanagedObject).realmSet$saved(((DrawPathRealmProxyInterface) realmObject).realmGet$saved());
        ((DrawPathRealmProxyInterface) unmanagedObject).realmSet$completed(((DrawPathRealmProxyInterface) realmObject).realmGet$completed());
        ((DrawPathRealmProxyInterface) unmanagedObject).realmSet$color(((DrawPathRealmProxyInterface) realmObject).realmGet$color());
        ((DrawPathRealmProxyInterface) unmanagedObject).realmSet$bushsize(((DrawPathRealmProxyInterface) realmObject).realmGet$bushsize());

        // Deep copy of points
        if (currentDepth == maxDepth) {
            ((DrawPathRealmProxyInterface) unmanagedObject).realmSet$points(null);
        } else {
            RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> managedpointsList = ((DrawPathRealmProxyInterface) realmObject).realmGet$points();
            RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> unmanagedpointsList = new RealmList<com.hosung.drawpadandepubreader.models.DrawPoint>();
            ((DrawPathRealmProxyInterface) unmanagedObject).realmSet$points(unmanagedpointsList);
            int nextDepth = currentDepth + 1;
            int size = managedpointsList.size();
            for (int i = 0; i < size; i++) {
                com.hosung.drawpadandepubreader.models.DrawPoint item = DrawPointRealmProxy.createDetachedCopy(managedpointsList.get(i), nextDepth, maxDepth, cache);
                unmanagedpointsList.add(item);
            }
        }
        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("DrawPath = proxy[");
        stringBuilder.append("{saved:");
        stringBuilder.append(realmGet$saved());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{completed:");
        stringBuilder.append(realmGet$completed());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{color:");
        stringBuilder.append(realmGet$color() != null ? realmGet$color() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{bushsize:");
        stringBuilder.append(realmGet$bushsize() != null ? realmGet$bushsize() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{points:");
        stringBuilder.append("RealmList<DrawPoint>[").append(realmGet$points().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawPathRealmProxy aDrawPath = (DrawPathRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aDrawPath.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aDrawPath.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aDrawPath.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
