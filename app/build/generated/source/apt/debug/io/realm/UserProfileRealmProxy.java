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

public class UserProfileRealmProxy extends com.hosung.drawpadandepubreader.models.UserProfile
    implements RealmObjectProxy, UserProfileRealmProxyInterface {

    static final class UserProfileColumnInfo extends ColumnInfo {
        long idIndex;
        long nameIndex;
        long emailIndex;
        long passwdIndex;

        UserProfileColumnInfo(SharedRealm realm, Table table) {
            super(4);
            this.idIndex = addColumnDetails(table, "id", RealmFieldType.INTEGER);
            this.nameIndex = addColumnDetails(table, "name", RealmFieldType.STRING);
            this.emailIndex = addColumnDetails(table, "email", RealmFieldType.STRING);
            this.passwdIndex = addColumnDetails(table, "passwd", RealmFieldType.STRING);
        }

        UserProfileColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new UserProfileColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final UserProfileColumnInfo src = (UserProfileColumnInfo) rawSrc;
            final UserProfileColumnInfo dst = (UserProfileColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.nameIndex = src.nameIndex;
            dst.emailIndex = src.emailIndex;
            dst.passwdIndex = src.passwdIndex;
        }
    }

    private UserProfileColumnInfo columnInfo;
    private ProxyState<com.hosung.drawpadandepubreader.models.UserProfile> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("email");
        fieldNames.add("passwd");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    UserProfileRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (UserProfileColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.hosung.drawpadandepubreader.models.UserProfile>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.idIndex);
    }

    @Override
    public void realmSet$id(int value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'id' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameIndex);
    }

    @Override
    public void realmSet$name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'name' to null.");
            }
            row.getTable().setString(columnInfo.nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'name' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.nameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$email() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.emailIndex);
    }

    @Override
    public void realmSet$email(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.emailIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.emailIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.emailIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.emailIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$passwd() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.passwdIndex);
    }

    @Override
    public void realmSet$passwd(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.passwdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.passwdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.passwdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.passwdIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("UserProfile")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("UserProfile");
            realmObjectSchema.add("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("passwd", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            return realmObjectSchema;
        }
        return realmSchema.get("UserProfile");
    }

    public static UserProfileColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_UserProfile")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'UserProfile' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_UserProfile");
        final long columnCount = table.getColumnCount();
        if (columnCount != 4) {
            if (columnCount < 4) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 4 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 4 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 4 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final UserProfileColumnInfo columnInfo = new UserProfileColumnInfo(sharedRealm, table);

        if (!table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary key not defined for field 'id' in existing Realm file. @PrimaryKey was added.");
        } else {
            if (table.getPrimaryKey() != columnInfo.idIndex) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key annotation definition was changed, from field " + table.getColumnName(table.getPrimaryKey()) + " to field id");
            }
        }

        if (!columnTypes.containsKey("id")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("id") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'id' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.idIndex) && table.findFirstNull(columnInfo.idIndex) != Table.NO_MATCH) {
            throw new IllegalStateException("Cannot migrate an object with null value in field 'id'. Either maintain the same type for primary key field 'id', or remove the object with null value before migration.");
        }
        if (!table.hasSearchIndex(table.getColumnIndex("id"))) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Index not defined for field 'id' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
        }
        if (!columnTypes.containsKey("name")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'name' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("name") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'name' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.nameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'name' does support null values in the existing Realm file. Remove @Required or @PrimaryKey from field 'name' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("email")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'email' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("email") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'email' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.emailIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'email' is required. Either set @Required to field 'email' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("passwd")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'passwd' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("passwd") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'passwd' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.passwdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'passwd' is required. Either set @Required to field 'passwd' or migrate using RealmObjectSchema.setNullable().");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_UserProfile";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.hosung.drawpadandepubreader.models.UserProfile createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.hosung.drawpadandepubreader.models.UserProfile obj = null;
        if (update) {
            Table table = realm.getTable(com.hosung.drawpadandepubreader.models.UserProfile.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.UserProfile.class), false, Collections.<String> emptyList());
                    obj = new io.realm.UserProfileRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.UserProfileRealmProxy) realm.createObjectInternal(com.hosung.drawpadandepubreader.models.UserProfile.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.UserProfileRealmProxy) realm.createObjectInternal(com.hosung.drawpadandepubreader.models.UserProfile.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                ((UserProfileRealmProxyInterface) obj).realmSet$name(null);
            } else {
                ((UserProfileRealmProxyInterface) obj).realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("email")) {
            if (json.isNull("email")) {
                ((UserProfileRealmProxyInterface) obj).realmSet$email(null);
            } else {
                ((UserProfileRealmProxyInterface) obj).realmSet$email((String) json.getString("email"));
            }
        }
        if (json.has("passwd")) {
            if (json.isNull("passwd")) {
                ((UserProfileRealmProxyInterface) obj).realmSet$passwd(null);
            } else {
                ((UserProfileRealmProxyInterface) obj).realmSet$passwd((String) json.getString("passwd"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.hosung.drawpadandepubreader.models.UserProfile createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        com.hosung.drawpadandepubreader.models.UserProfile obj = new com.hosung.drawpadandepubreader.models.UserProfile();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                } else {
                    ((UserProfileRealmProxyInterface) obj).realmSet$id((int) reader.nextInt());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UserProfileRealmProxyInterface) obj).realmSet$name(null);
                } else {
                    ((UserProfileRealmProxyInterface) obj).realmSet$name((String) reader.nextString());
                }
            } else if (name.equals("email")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UserProfileRealmProxyInterface) obj).realmSet$email(null);
                } else {
                    ((UserProfileRealmProxyInterface) obj).realmSet$email((String) reader.nextString());
                }
            } else if (name.equals("passwd")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UserProfileRealmProxyInterface) obj).realmSet$passwd(null);
                } else {
                    ((UserProfileRealmProxyInterface) obj).realmSet$passwd((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
        }
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.hosung.drawpadandepubreader.models.UserProfile copyOrUpdate(Realm realm, com.hosung.drawpadandepubreader.models.UserProfile object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.hosung.drawpadandepubreader.models.UserProfile) cachedRealmObject;
        } else {
            com.hosung.drawpadandepubreader.models.UserProfile realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(com.hosung.drawpadandepubreader.models.UserProfile.class);
                long pkColumnIndex = table.getPrimaryKey();
                long rowIndex = table.findFirstLong(pkColumnIndex, ((UserProfileRealmProxyInterface) object).realmGet$id());
                if (rowIndex != Table.NO_MATCH) {
                    try {
                        objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.UserProfile.class), false, Collections.<String> emptyList());
                        realmObject = new io.realm.UserProfileRealmProxy();
                        cache.put(object, (RealmObjectProxy) realmObject);
                    } finally {
                        objectContext.clear();
                    }
                } else {
                    canUpdate = false;
                }
            }

            if (canUpdate) {
                return update(realm, realmObject, object, cache);
            } else {
                return copy(realm, object, update, cache);
            }
        }
    }

    public static com.hosung.drawpadandepubreader.models.UserProfile copy(Realm realm, com.hosung.drawpadandepubreader.models.UserProfile newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.hosung.drawpadandepubreader.models.UserProfile) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.hosung.drawpadandepubreader.models.UserProfile realmObject = realm.createObjectInternal(com.hosung.drawpadandepubreader.models.UserProfile.class, ((UserProfileRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((UserProfileRealmProxyInterface) realmObject).realmSet$name(((UserProfileRealmProxyInterface) newObject).realmGet$name());
            ((UserProfileRealmProxyInterface) realmObject).realmSet$email(((UserProfileRealmProxyInterface) newObject).realmGet$email());
            ((UserProfileRealmProxyInterface) realmObject).realmSet$passwd(((UserProfileRealmProxyInterface) newObject).realmGet$passwd());
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.hosung.drawpadandepubreader.models.UserProfile object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.UserProfile.class);
        long tableNativePtr = table.getNativePtr();
        UserProfileColumnInfo columnInfo = (UserProfileColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.UserProfile.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((UserProfileRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((UserProfileRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(realm.sharedRealm, table, ((UserProfileRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$name = ((UserProfileRealmProxyInterface)object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }
        String realmGet$email = ((UserProfileRealmProxyInterface)object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
        }
        String realmGet$passwd = ((UserProfileRealmProxyInterface)object).realmGet$passwd();
        if (realmGet$passwd != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.passwdIndex, rowIndex, realmGet$passwd, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.UserProfile.class);
        long tableNativePtr = table.getNativePtr();
        UserProfileColumnInfo columnInfo = (UserProfileColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.UserProfile.class);
        long pkColumnIndex = table.getPrimaryKey();
        com.hosung.drawpadandepubreader.models.UserProfile object = null;
        while (objects.hasNext()) {
            object = (com.hosung.drawpadandepubreader.models.UserProfile) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.NO_MATCH;
                Object primaryKeyValue = ((UserProfileRealmProxyInterface) object).realmGet$id();
                if (primaryKeyValue != null) {
                    rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((UserProfileRealmProxyInterface) object).realmGet$id());
                }
                if (rowIndex == Table.NO_MATCH) {
                    rowIndex = OsObject.createRowWithPrimaryKey(realm.sharedRealm, table, ((UserProfileRealmProxyInterface) object).realmGet$id());
                } else {
                    Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
                }
                cache.put(object, rowIndex);
                String realmGet$name = ((UserProfileRealmProxyInterface)object).realmGet$name();
                if (realmGet$name != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
                }
                String realmGet$email = ((UserProfileRealmProxyInterface)object).realmGet$email();
                if (realmGet$email != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
                }
                String realmGet$passwd = ((UserProfileRealmProxyInterface)object).realmGet$passwd();
                if (realmGet$passwd != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.passwdIndex, rowIndex, realmGet$passwd, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.hosung.drawpadandepubreader.models.UserProfile object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.UserProfile.class);
        long tableNativePtr = table.getNativePtr();
        UserProfileColumnInfo columnInfo = (UserProfileColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.UserProfile.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((UserProfileRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((UserProfileRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(realm.sharedRealm, table, ((UserProfileRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        String realmGet$name = ((UserProfileRealmProxyInterface)object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }
        String realmGet$email = ((UserProfileRealmProxyInterface)object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.emailIndex, rowIndex, false);
        }
        String realmGet$passwd = ((UserProfileRealmProxyInterface)object).realmGet$passwd();
        if (realmGet$passwd != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.passwdIndex, rowIndex, realmGet$passwd, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.passwdIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.UserProfile.class);
        long tableNativePtr = table.getNativePtr();
        UserProfileColumnInfo columnInfo = (UserProfileColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.UserProfile.class);
        long pkColumnIndex = table.getPrimaryKey();
        com.hosung.drawpadandepubreader.models.UserProfile object = null;
        while (objects.hasNext()) {
            object = (com.hosung.drawpadandepubreader.models.UserProfile) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.NO_MATCH;
                Object primaryKeyValue = ((UserProfileRealmProxyInterface) object).realmGet$id();
                if (primaryKeyValue != null) {
                    rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((UserProfileRealmProxyInterface) object).realmGet$id());
                }
                if (rowIndex == Table.NO_MATCH) {
                    rowIndex = OsObject.createRowWithPrimaryKey(realm.sharedRealm, table, ((UserProfileRealmProxyInterface) object).realmGet$id());
                }
                cache.put(object, rowIndex);
                String realmGet$name = ((UserProfileRealmProxyInterface)object).realmGet$name();
                if (realmGet$name != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
                }
                String realmGet$email = ((UserProfileRealmProxyInterface)object).realmGet$email();
                if (realmGet$email != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.emailIndex, rowIndex, false);
                }
                String realmGet$passwd = ((UserProfileRealmProxyInterface)object).realmGet$passwd();
                if (realmGet$passwd != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.passwdIndex, rowIndex, realmGet$passwd, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.passwdIndex, rowIndex, false);
                }
            }
        }
    }

    public static com.hosung.drawpadandepubreader.models.UserProfile createDetachedCopy(com.hosung.drawpadandepubreader.models.UserProfile realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.hosung.drawpadandepubreader.models.UserProfile unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.hosung.drawpadandepubreader.models.UserProfile)cachedObject.object;
            } else {
                unmanagedObject = (com.hosung.drawpadandepubreader.models.UserProfile)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.hosung.drawpadandepubreader.models.UserProfile();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((UserProfileRealmProxyInterface) unmanagedObject).realmSet$id(((UserProfileRealmProxyInterface) realmObject).realmGet$id());
        ((UserProfileRealmProxyInterface) unmanagedObject).realmSet$name(((UserProfileRealmProxyInterface) realmObject).realmGet$name());
        ((UserProfileRealmProxyInterface) unmanagedObject).realmSet$email(((UserProfileRealmProxyInterface) realmObject).realmGet$email());
        ((UserProfileRealmProxyInterface) unmanagedObject).realmSet$passwd(((UserProfileRealmProxyInterface) realmObject).realmGet$passwd());
        return unmanagedObject;
    }

    static com.hosung.drawpadandepubreader.models.UserProfile update(Realm realm, com.hosung.drawpadandepubreader.models.UserProfile realmObject, com.hosung.drawpadandepubreader.models.UserProfile newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((UserProfileRealmProxyInterface) realmObject).realmSet$name(((UserProfileRealmProxyInterface) newObject).realmGet$name());
        ((UserProfileRealmProxyInterface) realmObject).realmSet$email(((UserProfileRealmProxyInterface) newObject).realmGet$email());
        ((UserProfileRealmProxyInterface) realmObject).realmSet$passwd(((UserProfileRealmProxyInterface) newObject).realmGet$passwd());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("UserProfile = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{email:");
        stringBuilder.append(realmGet$email() != null ? realmGet$email() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{passwd:");
        stringBuilder.append(realmGet$passwd() != null ? realmGet$passwd() : "null");
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
        UserProfileRealmProxy aUserProfile = (UserProfileRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aUserProfile.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aUserProfile.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aUserProfile.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
