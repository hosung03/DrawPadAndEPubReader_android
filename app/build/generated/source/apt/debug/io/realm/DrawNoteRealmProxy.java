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

public class DrawNoteRealmProxy extends com.hosung.drawpadandepubreader.models.DrawNote
    implements RealmObjectProxy, DrawNoteRealmProxyInterface {

    static final class DrawNoteColumnInfo extends ColumnInfo {
        long idIndex;
        long savedIndex;
        long userIndex;
        long titleIndex;
        long pathsIndex;

        DrawNoteColumnInfo(SharedRealm realm, Table table) {
            super(5);
            this.idIndex = addColumnDetails(table, "id", RealmFieldType.INTEGER);
            this.savedIndex = addColumnDetails(table, "saved", RealmFieldType.BOOLEAN);
            this.userIndex = addColumnDetails(table, "user", RealmFieldType.STRING);
            this.titleIndex = addColumnDetails(table, "title", RealmFieldType.STRING);
            this.pathsIndex = addColumnDetails(table, "paths", RealmFieldType.LIST);
        }

        DrawNoteColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new DrawNoteColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final DrawNoteColumnInfo src = (DrawNoteColumnInfo) rawSrc;
            final DrawNoteColumnInfo dst = (DrawNoteColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.savedIndex = src.savedIndex;
            dst.userIndex = src.userIndex;
            dst.titleIndex = src.titleIndex;
            dst.pathsIndex = src.pathsIndex;
        }
    }

    private DrawNoteColumnInfo columnInfo;
    private ProxyState<com.hosung.drawpadandepubreader.models.DrawNote> proxyState;
    private RealmList<com.hosung.drawpadandepubreader.models.DrawPath> pathsRealmList;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("saved");
        fieldNames.add("user");
        fieldNames.add("title");
        fieldNames.add("paths");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    DrawNoteRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (DrawNoteColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.hosung.drawpadandepubreader.models.DrawNote>(this);
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
    public String realmGet$user() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.userIndex);
    }

    @Override
    public void realmSet$user(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'user' to null.");
            }
            row.getTable().setString(columnInfo.userIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            throw new IllegalArgumentException("Trying to set non-nullable field 'user' to null.");
        }
        proxyState.getRow$realm().setString(columnInfo.userIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$title() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.titleIndex);
    }

    @Override
    public void realmSet$title(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.titleIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.titleIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.titleIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.titleIndex, value);
    }

    @Override
    public RealmList<com.hosung.drawpadandepubreader.models.DrawPath> realmGet$paths() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (pathsRealmList != null) {
            return pathsRealmList;
        } else {
            LinkView linkView = proxyState.getRow$realm().getLinkList(columnInfo.pathsIndex);
            pathsRealmList = new RealmList<com.hosung.drawpadandepubreader.models.DrawPath>(com.hosung.drawpadandepubreader.models.DrawPath.class, linkView, proxyState.getRealm$realm());
            return pathsRealmList;
        }
    }

    @Override
    public void realmSet$paths(RealmList<com.hosung.drawpadandepubreader.models.DrawPath> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("paths")) {
                return;
            }
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.hosung.drawpadandepubreader.models.DrawPath> original = value;
                value = new RealmList<com.hosung.drawpadandepubreader.models.DrawPath>();
                for (com.hosung.drawpadandepubreader.models.DrawPath item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        LinkView links = proxyState.getRow$realm().getLinkList(columnInfo.pathsIndex);
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
        if (!realmSchema.contains("DrawNote")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("DrawNote");
            realmObjectSchema.add("id", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("saved", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("user", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            if (!realmSchema.contains("DrawPath")) {
                DrawPathRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add("paths", RealmFieldType.LIST, realmSchema.get("DrawPath"));
            return realmObjectSchema;
        }
        return realmSchema.get("DrawNote");
    }

    public static DrawNoteColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_DrawNote")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'DrawNote' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_DrawNote");
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

        final DrawNoteColumnInfo columnInfo = new DrawNoteColumnInfo(sharedRealm, table);

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
        if (!columnTypes.containsKey("saved")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'saved' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("saved") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'saved' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.savedIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'saved' does support null values in the existing Realm file. Use corresponding boxed type for field 'saved' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("user")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'user' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("user") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'user' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.userIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'user' does support null values in the existing Realm file. Remove @Required or @PrimaryKey from field 'user' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("title")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'title' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("title") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'title' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.titleIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'title' is required. Either set @Required to field 'title' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("paths")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'paths'");
        }
        if (columnTypes.get("paths") != RealmFieldType.LIST) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'DrawPath' for field 'paths'");
        }
        if (!sharedRealm.hasTable("class_DrawPath")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_DrawPath' for field 'paths'");
        }
        Table table_4 = sharedRealm.getTable("class_DrawPath");
        if (!table.getLinkTarget(columnInfo.pathsIndex).hasSameSchema(table_4)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'paths': '" + table.getLinkTarget(columnInfo.pathsIndex).getName() + "' expected - was '" + table_4.getName() + "'");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_DrawNote";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.hosung.drawpadandepubreader.models.DrawNote createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        com.hosung.drawpadandepubreader.models.DrawNote obj = null;
        if (update) {
            Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawNote.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("id")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawNote.class), false, Collections.<String> emptyList());
                    obj = new io.realm.DrawNoteRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("paths")) {
                excludeFields.add("paths");
            }
            if (json.has("id")) {
                if (json.isNull("id")) {
                    obj = (io.realm.DrawNoteRealmProxy) realm.createObjectInternal(com.hosung.drawpadandepubreader.models.DrawNote.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.DrawNoteRealmProxy) realm.createObjectInternal(com.hosung.drawpadandepubreader.models.DrawNote.class, json.getInt("id"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'id'.");
            }
        }
        if (json.has("saved")) {
            if (json.isNull("saved")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'saved' to null.");
            } else {
                ((DrawNoteRealmProxyInterface) obj).realmSet$saved((boolean) json.getBoolean("saved"));
            }
        }
        if (json.has("user")) {
            if (json.isNull("user")) {
                ((DrawNoteRealmProxyInterface) obj).realmSet$user(null);
            } else {
                ((DrawNoteRealmProxyInterface) obj).realmSet$user((String) json.getString("user"));
            }
        }
        if (json.has("title")) {
            if (json.isNull("title")) {
                ((DrawNoteRealmProxyInterface) obj).realmSet$title(null);
            } else {
                ((DrawNoteRealmProxyInterface) obj).realmSet$title((String) json.getString("title"));
            }
        }
        if (json.has("paths")) {
            if (json.isNull("paths")) {
                ((DrawNoteRealmProxyInterface) obj).realmSet$paths(null);
            } else {
                ((DrawNoteRealmProxyInterface) obj).realmGet$paths().clear();
                JSONArray array = json.getJSONArray("paths");
                for (int i = 0; i < array.length(); i++) {
                    com.hosung.drawpadandepubreader.models.DrawPath item = DrawPathRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    ((DrawNoteRealmProxyInterface) obj).realmGet$paths().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.hosung.drawpadandepubreader.models.DrawNote createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        com.hosung.drawpadandepubreader.models.DrawNote obj = new com.hosung.drawpadandepubreader.models.DrawNote();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'id' to null.");
                } else {
                    ((DrawNoteRealmProxyInterface) obj).realmSet$id((int) reader.nextInt());
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("saved")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'saved' to null.");
                } else {
                    ((DrawNoteRealmProxyInterface) obj).realmSet$saved((boolean) reader.nextBoolean());
                }
            } else if (name.equals("user")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((DrawNoteRealmProxyInterface) obj).realmSet$user(null);
                } else {
                    ((DrawNoteRealmProxyInterface) obj).realmSet$user((String) reader.nextString());
                }
            } else if (name.equals("title")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((DrawNoteRealmProxyInterface) obj).realmSet$title(null);
                } else {
                    ((DrawNoteRealmProxyInterface) obj).realmSet$title((String) reader.nextString());
                }
            } else if (name.equals("paths")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((DrawNoteRealmProxyInterface) obj).realmSet$paths(null);
                } else {
                    ((DrawNoteRealmProxyInterface) obj).realmSet$paths(new RealmList<com.hosung.drawpadandepubreader.models.DrawPath>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.hosung.drawpadandepubreader.models.DrawPath item = DrawPathRealmProxy.createUsingJsonStream(realm, reader);
                        ((DrawNoteRealmProxyInterface) obj).realmGet$paths().add(item);
                    }
                    reader.endArray();
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

    public static com.hosung.drawpadandepubreader.models.DrawNote copyOrUpdate(Realm realm, com.hosung.drawpadandepubreader.models.DrawNote object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.hosung.drawpadandepubreader.models.DrawNote) cachedRealmObject;
        } else {
            com.hosung.drawpadandepubreader.models.DrawNote realmObject = null;
            boolean canUpdate = update;
            if (canUpdate) {
                Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawNote.class);
                long pkColumnIndex = table.getPrimaryKey();
                long rowIndex = table.findFirstLong(pkColumnIndex, ((DrawNoteRealmProxyInterface) object).realmGet$id());
                if (rowIndex != Table.NO_MATCH) {
                    try {
                        objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawNote.class), false, Collections.<String> emptyList());
                        realmObject = new io.realm.DrawNoteRealmProxy();
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

    public static com.hosung.drawpadandepubreader.models.DrawNote copy(Realm realm, com.hosung.drawpadandepubreader.models.DrawNote newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.hosung.drawpadandepubreader.models.DrawNote) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.hosung.drawpadandepubreader.models.DrawNote realmObject = realm.createObjectInternal(com.hosung.drawpadandepubreader.models.DrawNote.class, ((DrawNoteRealmProxyInterface) newObject).realmGet$id(), false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((DrawNoteRealmProxyInterface) realmObject).realmSet$saved(((DrawNoteRealmProxyInterface) newObject).realmGet$saved());
            ((DrawNoteRealmProxyInterface) realmObject).realmSet$user(((DrawNoteRealmProxyInterface) newObject).realmGet$user());
            ((DrawNoteRealmProxyInterface) realmObject).realmSet$title(((DrawNoteRealmProxyInterface) newObject).realmGet$title());

            RealmList<com.hosung.drawpadandepubreader.models.DrawPath> pathsList = ((DrawNoteRealmProxyInterface) newObject).realmGet$paths();
            if (pathsList != null) {
                RealmList<com.hosung.drawpadandepubreader.models.DrawPath> pathsRealmList = ((DrawNoteRealmProxyInterface) realmObject).realmGet$paths();
                for (int i = 0; i < pathsList.size(); i++) {
                    com.hosung.drawpadandepubreader.models.DrawPath pathsItem = pathsList.get(i);
                    com.hosung.drawpadandepubreader.models.DrawPath cachepaths = (com.hosung.drawpadandepubreader.models.DrawPath) cache.get(pathsItem);
                    if (cachepaths != null) {
                        pathsRealmList.add(cachepaths);
                    } else {
                        pathsRealmList.add(DrawPathRealmProxy.copyOrUpdate(realm, pathsList.get(i), update, cache));
                    }
                }
            }

            return realmObject;
        }
    }

    public static long insert(Realm realm, com.hosung.drawpadandepubreader.models.DrawNote object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawNote.class);
        long tableNativePtr = table.getNativePtr();
        DrawNoteColumnInfo columnInfo = (DrawNoteColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawNote.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((DrawNoteRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((DrawNoteRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(realm.sharedRealm, table, ((DrawNoteRealmProxyInterface) object).realmGet$id());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.savedIndex, rowIndex, ((DrawNoteRealmProxyInterface)object).realmGet$saved(), false);
        String realmGet$user = ((DrawNoteRealmProxyInterface)object).realmGet$user();
        if (realmGet$user != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.userIndex, rowIndex, realmGet$user, false);
        }
        String realmGet$title = ((DrawNoteRealmProxyInterface)object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        }

        RealmList<com.hosung.drawpadandepubreader.models.DrawPath> pathsList = ((DrawNoteRealmProxyInterface) object).realmGet$paths();
        if (pathsList != null) {
            long pathsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.pathsIndex, rowIndex);
            for (com.hosung.drawpadandepubreader.models.DrawPath pathsItem : pathsList) {
                Long cacheItemIndexpaths = cache.get(pathsItem);
                if (cacheItemIndexpaths == null) {
                    cacheItemIndexpaths = DrawPathRealmProxy.insert(realm, pathsItem, cache);
                }
                LinkView.nativeAdd(pathsNativeLinkViewPtr, cacheItemIndexpaths);
            }
        }

        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawNote.class);
        long tableNativePtr = table.getNativePtr();
        DrawNoteColumnInfo columnInfo = (DrawNoteColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawNote.class);
        long pkColumnIndex = table.getPrimaryKey();
        com.hosung.drawpadandepubreader.models.DrawNote object = null;
        while (objects.hasNext()) {
            object = (com.hosung.drawpadandepubreader.models.DrawNote) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.NO_MATCH;
                Object primaryKeyValue = ((DrawNoteRealmProxyInterface) object).realmGet$id();
                if (primaryKeyValue != null) {
                    rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((DrawNoteRealmProxyInterface) object).realmGet$id());
                }
                if (rowIndex == Table.NO_MATCH) {
                    rowIndex = OsObject.createRowWithPrimaryKey(realm.sharedRealm, table, ((DrawNoteRealmProxyInterface) object).realmGet$id());
                } else {
                    Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
                }
                cache.put(object, rowIndex);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.savedIndex, rowIndex, ((DrawNoteRealmProxyInterface)object).realmGet$saved(), false);
                String realmGet$user = ((DrawNoteRealmProxyInterface)object).realmGet$user();
                if (realmGet$user != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.userIndex, rowIndex, realmGet$user, false);
                }
                String realmGet$title = ((DrawNoteRealmProxyInterface)object).realmGet$title();
                if (realmGet$title != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
                }

                RealmList<com.hosung.drawpadandepubreader.models.DrawPath> pathsList = ((DrawNoteRealmProxyInterface) object).realmGet$paths();
                if (pathsList != null) {
                    long pathsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.pathsIndex, rowIndex);
                    for (com.hosung.drawpadandepubreader.models.DrawPath pathsItem : pathsList) {
                        Long cacheItemIndexpaths = cache.get(pathsItem);
                        if (cacheItemIndexpaths == null) {
                            cacheItemIndexpaths = DrawPathRealmProxy.insert(realm, pathsItem, cache);
                        }
                        LinkView.nativeAdd(pathsNativeLinkViewPtr, cacheItemIndexpaths);
                    }
                }

            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.hosung.drawpadandepubreader.models.DrawNote object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawNote.class);
        long tableNativePtr = table.getNativePtr();
        DrawNoteColumnInfo columnInfo = (DrawNoteColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawNote.class);
        long pkColumnIndex = table.getPrimaryKey();
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((DrawNoteRealmProxyInterface) object).realmGet$id();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((DrawNoteRealmProxyInterface) object).realmGet$id());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(realm.sharedRealm, table, ((DrawNoteRealmProxyInterface) object).realmGet$id());
        }
        cache.put(object, rowIndex);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.savedIndex, rowIndex, ((DrawNoteRealmProxyInterface)object).realmGet$saved(), false);
        String realmGet$user = ((DrawNoteRealmProxyInterface)object).realmGet$user();
        if (realmGet$user != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.userIndex, rowIndex, realmGet$user, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.userIndex, rowIndex, false);
        }
        String realmGet$title = ((DrawNoteRealmProxyInterface)object).realmGet$title();
        if (realmGet$title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
        }

        long pathsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.pathsIndex, rowIndex);
        LinkView.nativeClear(pathsNativeLinkViewPtr);
        RealmList<com.hosung.drawpadandepubreader.models.DrawPath> pathsList = ((DrawNoteRealmProxyInterface) object).realmGet$paths();
        if (pathsList != null) {
            for (com.hosung.drawpadandepubreader.models.DrawPath pathsItem : pathsList) {
                Long cacheItemIndexpaths = cache.get(pathsItem);
                if (cacheItemIndexpaths == null) {
                    cacheItemIndexpaths = DrawPathRealmProxy.insertOrUpdate(realm, pathsItem, cache);
                }
                LinkView.nativeAdd(pathsNativeLinkViewPtr, cacheItemIndexpaths);
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.hosung.drawpadandepubreader.models.DrawNote.class);
        long tableNativePtr = table.getNativePtr();
        DrawNoteColumnInfo columnInfo = (DrawNoteColumnInfo) realm.schema.getColumnInfo(com.hosung.drawpadandepubreader.models.DrawNote.class);
        long pkColumnIndex = table.getPrimaryKey();
        com.hosung.drawpadandepubreader.models.DrawNote object = null;
        while (objects.hasNext()) {
            object = (com.hosung.drawpadandepubreader.models.DrawNote) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.NO_MATCH;
                Object primaryKeyValue = ((DrawNoteRealmProxyInterface) object).realmGet$id();
                if (primaryKeyValue != null) {
                    rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((DrawNoteRealmProxyInterface) object).realmGet$id());
                }
                if (rowIndex == Table.NO_MATCH) {
                    rowIndex = OsObject.createRowWithPrimaryKey(realm.sharedRealm, table, ((DrawNoteRealmProxyInterface) object).realmGet$id());
                }
                cache.put(object, rowIndex);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.savedIndex, rowIndex, ((DrawNoteRealmProxyInterface)object).realmGet$saved(), false);
                String realmGet$user = ((DrawNoteRealmProxyInterface)object).realmGet$user();
                if (realmGet$user != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.userIndex, rowIndex, realmGet$user, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.userIndex, rowIndex, false);
                }
                String realmGet$title = ((DrawNoteRealmProxyInterface)object).realmGet$title();
                if (realmGet$title != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.titleIndex, rowIndex, realmGet$title, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.titleIndex, rowIndex, false);
                }

                long pathsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.pathsIndex, rowIndex);
                LinkView.nativeClear(pathsNativeLinkViewPtr);
                RealmList<com.hosung.drawpadandepubreader.models.DrawPath> pathsList = ((DrawNoteRealmProxyInterface) object).realmGet$paths();
                if (pathsList != null) {
                    for (com.hosung.drawpadandepubreader.models.DrawPath pathsItem : pathsList) {
                        Long cacheItemIndexpaths = cache.get(pathsItem);
                        if (cacheItemIndexpaths == null) {
                            cacheItemIndexpaths = DrawPathRealmProxy.insertOrUpdate(realm, pathsItem, cache);
                        }
                        LinkView.nativeAdd(pathsNativeLinkViewPtr, cacheItemIndexpaths);
                    }
                }

            }
        }
    }

    public static com.hosung.drawpadandepubreader.models.DrawNote createDetachedCopy(com.hosung.drawpadandepubreader.models.DrawNote realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.hosung.drawpadandepubreader.models.DrawNote unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.hosung.drawpadandepubreader.models.DrawNote)cachedObject.object;
            } else {
                unmanagedObject = (com.hosung.drawpadandepubreader.models.DrawNote)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.hosung.drawpadandepubreader.models.DrawNote();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((DrawNoteRealmProxyInterface) unmanagedObject).realmSet$id(((DrawNoteRealmProxyInterface) realmObject).realmGet$id());
        ((DrawNoteRealmProxyInterface) unmanagedObject).realmSet$saved(((DrawNoteRealmProxyInterface) realmObject).realmGet$saved());
        ((DrawNoteRealmProxyInterface) unmanagedObject).realmSet$user(((DrawNoteRealmProxyInterface) realmObject).realmGet$user());
        ((DrawNoteRealmProxyInterface) unmanagedObject).realmSet$title(((DrawNoteRealmProxyInterface) realmObject).realmGet$title());

        // Deep copy of paths
        if (currentDepth == maxDepth) {
            ((DrawNoteRealmProxyInterface) unmanagedObject).realmSet$paths(null);
        } else {
            RealmList<com.hosung.drawpadandepubreader.models.DrawPath> managedpathsList = ((DrawNoteRealmProxyInterface) realmObject).realmGet$paths();
            RealmList<com.hosung.drawpadandepubreader.models.DrawPath> unmanagedpathsList = new RealmList<com.hosung.drawpadandepubreader.models.DrawPath>();
            ((DrawNoteRealmProxyInterface) unmanagedObject).realmSet$paths(unmanagedpathsList);
            int nextDepth = currentDepth + 1;
            int size = managedpathsList.size();
            for (int i = 0; i < size; i++) {
                com.hosung.drawpadandepubreader.models.DrawPath item = DrawPathRealmProxy.createDetachedCopy(managedpathsList.get(i), nextDepth, maxDepth, cache);
                unmanagedpathsList.add(item);
            }
        }
        return unmanagedObject;
    }

    static com.hosung.drawpadandepubreader.models.DrawNote update(Realm realm, com.hosung.drawpadandepubreader.models.DrawNote realmObject, com.hosung.drawpadandepubreader.models.DrawNote newObject, Map<RealmModel, RealmObjectProxy> cache) {
        ((DrawNoteRealmProxyInterface) realmObject).realmSet$saved(((DrawNoteRealmProxyInterface) newObject).realmGet$saved());
        ((DrawNoteRealmProxyInterface) realmObject).realmSet$user(((DrawNoteRealmProxyInterface) newObject).realmGet$user());
        ((DrawNoteRealmProxyInterface) realmObject).realmSet$title(((DrawNoteRealmProxyInterface) newObject).realmGet$title());
        RealmList<com.hosung.drawpadandepubreader.models.DrawPath> pathsList = ((DrawNoteRealmProxyInterface) newObject).realmGet$paths();
        RealmList<com.hosung.drawpadandepubreader.models.DrawPath> pathsRealmList = ((DrawNoteRealmProxyInterface) realmObject).realmGet$paths();
        pathsRealmList.clear();
        if (pathsList != null) {
            for (int i = 0; i < pathsList.size(); i++) {
                com.hosung.drawpadandepubreader.models.DrawPath pathsItem = pathsList.get(i);
                com.hosung.drawpadandepubreader.models.DrawPath cachepaths = (com.hosung.drawpadandepubreader.models.DrawPath) cache.get(pathsItem);
                if (cachepaths != null) {
                    pathsRealmList.add(cachepaths);
                } else {
                    pathsRealmList.add(DrawPathRealmProxy.copyOrUpdate(realm, pathsList.get(i), true, cache));
                }
            }
        }
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("DrawNote = proxy[");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{saved:");
        stringBuilder.append(realmGet$saved());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user:");
        stringBuilder.append(realmGet$user());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(realmGet$title() != null ? realmGet$title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{paths:");
        stringBuilder.append("RealmList<DrawPath>[").append(realmGet$paths().size()).append("]");
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
        DrawNoteRealmProxy aDrawNote = (DrawNoteRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aDrawNote.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aDrawNote.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aDrawNote.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
