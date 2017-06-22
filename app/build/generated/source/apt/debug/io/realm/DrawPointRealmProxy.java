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

public class DrawPointRealmProxy extends com.hosung.drawpadandepubreader.models.DrawPoint
    implements RealmObjectProxy, DrawPointRealmProxyInterface {

    static final class DrawPointColumnInfo extends ColumnInfo {
        long xIndex;
        long yIndex;

        DrawPointColumnInfo(SharedRealm realm, Table table) {
            super(2);
            this.xIndex = addColumnDetails(table, "x", RealmFieldType.DOUBLE);
            this.yIndex = addColumnDetails(table, "y", RealmFieldType.DOUBLE);
        }

        DrawPointColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new DrawPointColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final DrawPointColumnInfo src = (DrawPointColumnInfo) rawSrc;
            final DrawPointColumnInfo dst = (DrawPointColumnInfo) rawDst;
            dst.xIndex = src.xIndex;
            dst.yIndex = src.yIndex;
        }
    }

    private DrawPointColumnInfo columnInfo;
    private ProxyState<com.hosung.drawpadandepubreader.models.DrawPoint> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("x");
        fieldNames.add("y");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    DrawPointRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (DrawPointColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.hosung.drawpadandepubreader.models.DrawPoint>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public double realmGet$x() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.xIndex);
    }

    @Override
    public void realmSet$x(double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setDouble(columnInfo.xIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.xIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public double realmGet$y() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.yIndex);
    }

    @Override
    public void realmSet$y(double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setDouble(columnInfo.yIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.yIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("DrawPoint")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("DrawPoint");
            realmObjectSchema.add("x", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("y", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            return realmObjectSchema;
        }
        return realmSchema.get("DrawPoint");
    }

    public static DrawPointColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_DrawPoint")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'DrawPoint' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_DrawPoint");
        final long columnCount = table.getColumnCount();
        if (columnCount != 2) {
            if (columnCount < 2) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 2 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 2 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 2 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final DrawPointColumnInfo columnInfo = new DrawPointColumnInfo(sharedRealm, table);

        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }

        if (!columnTypes.containsKey("x")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'x' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("x") != RealmFieldType.DOUBLE) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'double' for field 'x' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.xIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'x' does support null values in the existing Realm file. Use corresponding boxed type for field 'x' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("y")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'y' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("y") != RealmFieldType.DOUBLE) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'double' for field 'y' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.yIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'y' does support null values in the existing Realm file. Use corresponding boxed type for field 'y' or migrate using RealmObjectSchema.setNullable().");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_DrawPoint";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.hosung.drawpadandepubreader.models.DrawPoint createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.hosung.drawpadandepubreader.models.DrawPoint obj = realm.createObjectInternal(com.hosung.drawpadandepubreader.models.DrawPoint.class, true, excludeFields);
        if (json.has("x")) {
            if (json.isNull("x")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'x' to null.");
            } else {
                ((DrawPointRealmProxyInterface) obj).realmSet$x((double) json.getDouble("x"));
            }
        }
        if (json.has("y")) {
            if (json.isNull("y")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'y' to null.");
            } else {
                ((DrawPointRealmProxyInterface) obj).realmSet$y((double) json.getDouble("y"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.hosung.drawpadandepubreader.models.DrawPoint createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.hosung.drawpadandepubreader.models.DrawPoint obj = new com.hosung.drawpadandepubreader.models.DrawPoint();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("x")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'x' to null.");
                } else {
                    ((DrawPointRealmProxyInterface) obj).realmSet$x((double) reader.nextDouble());
                }
            } else if (name.equals("y")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'y' to null.");
                } else {
                    ((DrawPointRealmProxyInterface) obj).realmSet$y((double) reader.nextDouble());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.hosung.drawpadandepubreader.models.DrawPoint copyOrUpdate(Realm realm, com.hosung.drawpadandepubreader.models.DrawPoint object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.hosung.drawpadandepubreader.models.DrawPoint) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.hosung.drawpadandepubreader.models.DrawPoint copy(Realm realm, com.hosung.drawpadandepubreader.models.DrawPoint newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.hosung.drawpadandepubreader.models.DrawPoint) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.hosung.drawpadandepubreader.models.DrawPoint realmObject = realm.createObjectInternal(com.hosung.drawpadandepubreader.models.DrawPoint.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((DrawPointRealmProxyInterface) realmObject).realmSet$x(((DrawPointRealmProxyInterface) newObject).realmGet$x());
            ((DrawPointRealmProxyInterface) realmObject).realmSet$y(((DrawPointRealmProxyInterface) newObject).realmGet$y());
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.hosung.drawpadandepubreader.models.DrawPoint object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawPoint.class);
        long tableNativePtr = table.getNativePtr();
        DrawPointColumnInfo columnInfo = (DrawPointColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawPoint.class);
        long rowIndex = OsObject.createRow(realm.sharedRealm, table);
        cache.put(object, rowIndex);
        Table.nativeSetDouble(tableNativePtr, columnInfo.xIndex, rowIndex, ((DrawPointRealmProxyInterface)object).realmGet$x(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.yIndex, rowIndex, ((DrawPointRealmProxyInterface)object).realmGet$y(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawPoint.class);
        long tableNativePtr = table.getNativePtr();
        DrawPointColumnInfo columnInfo = (DrawPointColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawPoint.class);
        com.hosung.drawpadandepubreader.models.DrawPoint object = null;
        while (objects.hasNext()) {
            object = (com.hosung.drawpadandepubreader.models.DrawPoint) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = OsObject.createRow(realm.sharedRealm, table);
                cache.put(object, rowIndex);
                Table.nativeSetDouble(tableNativePtr, columnInfo.xIndex, rowIndex, ((DrawPointRealmProxyInterface)object).realmGet$x(), false);
                Table.nativeSetDouble(tableNativePtr, columnInfo.yIndex, rowIndex, ((DrawPointRealmProxyInterface)object).realmGet$y(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.hosung.drawpadandepubreader.models.DrawPoint object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawPoint.class);
        long tableNativePtr = table.getNativePtr();
        DrawPointColumnInfo columnInfo = (DrawPointColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawPoint.class);
        long rowIndex = OsObject.createRow(realm.sharedRealm, table);
        cache.put(object, rowIndex);
        Table.nativeSetDouble(tableNativePtr, columnInfo.xIndex, rowIndex, ((DrawPointRealmProxyInterface)object).realmGet$x(), false);
        Table.nativeSetDouble(tableNativePtr, columnInfo.yIndex, rowIndex, ((DrawPointRealmProxyInterface)object).realmGet$y(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawPoint.class);
        long tableNativePtr = table.getNativePtr();
        DrawPointColumnInfo columnInfo = (DrawPointColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawPoint.class);
        com.hosung.drawpadandepubreader.models.DrawPoint object = null;
        while (objects.hasNext()) {
            object = (com.hosung.drawpadandepubreader.models.DrawPoint) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = OsObject.createRow(realm.sharedRealm, table);
                cache.put(object, rowIndex);
                Table.nativeSetDouble(tableNativePtr, columnInfo.xIndex, rowIndex, ((DrawPointRealmProxyInterface)object).realmGet$x(), false);
                Table.nativeSetDouble(tableNativePtr, columnInfo.yIndex, rowIndex, ((DrawPointRealmProxyInterface)object).realmGet$y(), false);
            }
        }
    }

    public static com.hosung.drawpadandepubreader.models.DrawPoint createDetachedCopy(com.hosung.drawpadandepubreader.models.DrawPoint realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.hosung.drawpadandepubreader.models.DrawPoint unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.hosung.drawpadandepubreader.models.DrawPoint)cachedObject.object;
            } else {
                unmanagedObject = (com.hosung.drawpadandepubreader.models.DrawPoint)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.hosung.drawpadandepubreader.models.DrawPoint();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((DrawPointRealmProxyInterface) unmanagedObject).realmSet$x(((DrawPointRealmProxyInterface) realmObject).realmGet$x());
        ((DrawPointRealmProxyInterface) unmanagedObject).realmSet$y(((DrawPointRealmProxyInterface) realmObject).realmGet$y());
        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("DrawPoint = proxy[");
        stringBuilder.append("{x:");
        stringBuilder.append(realmGet$x());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{y:");
        stringBuilder.append(realmGet$y());
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
        DrawPointRealmProxy aDrawPoint = (DrawPointRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aDrawPoint.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aDrawPoint.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aDrawPoint.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
