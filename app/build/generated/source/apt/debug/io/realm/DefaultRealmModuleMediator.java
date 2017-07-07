package io.realm;


import android.util.JsonReader;
import io.realm.RealmObjectSchema;
import io.realm.internal.ColumnInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>();
        modelClasses.add(com.hosung.drawpadandepubreader.models.DrawNote.class);
        modelClasses.add(com.hosung.drawpadandepubreader.models.UserProfile.class);
        modelClasses.add(com.hosung.drawpadandepubreader.models.DrawPoint.class);
        modelClasses.add(com.hosung.drawpadandepubreader.models.DrawPath.class);
        modelClasses.add(com.hosung.drawpadandepubreader.models.EPubHighLight.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public RealmObjectSchema createRealmObjectSchema(Class<? extends RealmModel> clazz, RealmSchema realmSchema) {
        checkClass(clazz);

        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
            return io.realm.DrawNoteRealmProxy.createRealmObjectSchema(realmSchema);
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
            return io.realm.UserProfileRealmProxy.createRealmObjectSchema(realmSchema);
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
            return io.realm.DrawPointRealmProxy.createRealmObjectSchema(realmSchema);
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
            return io.realm.DrawPathRealmProxy.createRealmObjectSchema(realmSchema);
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
            return io.realm.EPubHighLightRealmProxy.createRealmObjectSchema(realmSchema);
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public ColumnInfo validateTable(Class<? extends RealmModel> clazz, SharedRealm sharedRealm, boolean allowExtraColumns) {
        checkClass(clazz);

        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
            return io.realm.DrawNoteRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
            return io.realm.UserProfileRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
            return io.realm.DrawPointRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
            return io.realm.DrawPathRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
            return io.realm.EPubHighLightRealmProxy.validateTable(sharedRealm, allowExtraColumns);
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public List<String> getFieldNames(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
            return io.realm.DrawNoteRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
            return io.realm.UserProfileRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
            return io.realm.DrawPointRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
            return io.realm.DrawPathRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
            return io.realm.EPubHighLightRealmProxy.getFieldNames();
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public String getTableName(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
            return io.realm.DrawNoteRealmProxy.getTableName();
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
            return io.realm.UserProfileRealmProxy.getTableName();
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
            return io.realm.DrawPointRealmProxy.getTableName();
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
            return io.realm.DrawPathRealmProxy.getTableName();
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
            return io.realm.EPubHighLightRealmProxy.getTableName();
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
                return clazz.cast(new io.realm.DrawNoteRealmProxy());
            }
            if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
                return clazz.cast(new io.realm.UserProfileRealmProxy());
            }
            if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
                return clazz.cast(new io.realm.DrawPointRealmProxy());
            }
            if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
                return clazz.cast(new io.realm.DrawPathRealmProxy());
            }
            if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
                return clazz.cast(new io.realm.EPubHighLightRealmProxy());
            }
            throw getMissingProxyClassException(clazz);
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
            return clazz.cast(io.realm.DrawNoteRealmProxy.copyOrUpdate(realm, (com.hosung.drawpadandepubreader.models.DrawNote) obj, update, cache));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
            return clazz.cast(io.realm.UserProfileRealmProxy.copyOrUpdate(realm, (com.hosung.drawpadandepubreader.models.UserProfile) obj, update, cache));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
            return clazz.cast(io.realm.DrawPointRealmProxy.copyOrUpdate(realm, (com.hosung.drawpadandepubreader.models.DrawPoint) obj, update, cache));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
            return clazz.cast(io.realm.DrawPathRealmProxy.copyOrUpdate(realm, (com.hosung.drawpadandepubreader.models.DrawPath) obj, update, cache));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
            return clazz.cast(io.realm.EPubHighLightRealmProxy.copyOrUpdate(realm, (com.hosung.drawpadandepubreader.models.EPubHighLight) obj, update, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
            io.realm.DrawNoteRealmProxy.insert(realm, (com.hosung.drawpadandepubreader.models.DrawNote) object, cache);
        } else if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
            io.realm.UserProfileRealmProxy.insert(realm, (com.hosung.drawpadandepubreader.models.UserProfile) object, cache);
        } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
            io.realm.DrawPointRealmProxy.insert(realm, (com.hosung.drawpadandepubreader.models.DrawPoint) object, cache);
        } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
            io.realm.DrawPathRealmProxy.insert(realm, (com.hosung.drawpadandepubreader.models.DrawPath) object, cache);
        } else if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
            io.realm.EPubHighLightRealmProxy.insert(realm, (com.hosung.drawpadandepubreader.models.EPubHighLight) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
                io.realm.DrawNoteRealmProxy.insert(realm, (com.hosung.drawpadandepubreader.models.DrawNote) object, cache);
            } else if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
                io.realm.UserProfileRealmProxy.insert(realm, (com.hosung.drawpadandepubreader.models.UserProfile) object, cache);
            } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
                io.realm.DrawPointRealmProxy.insert(realm, (com.hosung.drawpadandepubreader.models.DrawPoint) object, cache);
            } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
                io.realm.DrawPathRealmProxy.insert(realm, (com.hosung.drawpadandepubreader.models.DrawPath) object, cache);
            } else if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
                io.realm.EPubHighLightRealmProxy.insert(realm, (com.hosung.drawpadandepubreader.models.EPubHighLight) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
                    io.realm.DrawNoteRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
                    io.realm.UserProfileRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
                    io.realm.DrawPointRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
                    io.realm.DrawPathRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
                    io.realm.EPubHighLightRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
            io.realm.DrawNoteRealmProxy.insertOrUpdate(realm, (com.hosung.drawpadandepubreader.models.DrawNote) obj, cache);
        } else if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
            io.realm.UserProfileRealmProxy.insertOrUpdate(realm, (com.hosung.drawpadandepubreader.models.UserProfile) obj, cache);
        } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
            io.realm.DrawPointRealmProxy.insertOrUpdate(realm, (com.hosung.drawpadandepubreader.models.DrawPoint) obj, cache);
        } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
            io.realm.DrawPathRealmProxy.insertOrUpdate(realm, (com.hosung.drawpadandepubreader.models.DrawPath) obj, cache);
        } else if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
            io.realm.EPubHighLightRealmProxy.insertOrUpdate(realm, (com.hosung.drawpadandepubreader.models.EPubHighLight) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
                io.realm.DrawNoteRealmProxy.insertOrUpdate(realm, (com.hosung.drawpadandepubreader.models.DrawNote) object, cache);
            } else if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
                io.realm.UserProfileRealmProxy.insertOrUpdate(realm, (com.hosung.drawpadandepubreader.models.UserProfile) object, cache);
            } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
                io.realm.DrawPointRealmProxy.insertOrUpdate(realm, (com.hosung.drawpadandepubreader.models.DrawPoint) object, cache);
            } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
                io.realm.DrawPathRealmProxy.insertOrUpdate(realm, (com.hosung.drawpadandepubreader.models.DrawPath) object, cache);
            } else if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
                io.realm.EPubHighLightRealmProxy.insertOrUpdate(realm, (com.hosung.drawpadandepubreader.models.EPubHighLight) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
                    io.realm.DrawNoteRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
                    io.realm.UserProfileRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
                    io.realm.DrawPointRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
                    io.realm.DrawPathRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
                    io.realm.EPubHighLightRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
            return clazz.cast(io.realm.DrawNoteRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
            return clazz.cast(io.realm.UserProfileRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
            return clazz.cast(io.realm.DrawPointRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
            return clazz.cast(io.realm.DrawPathRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
            return clazz.cast(io.realm.EPubHighLightRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
            return clazz.cast(io.realm.DrawNoteRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
            return clazz.cast(io.realm.UserProfileRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
            return clazz.cast(io.realm.DrawPointRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
            return clazz.cast(io.realm.DrawPathRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
            return clazz.cast(io.realm.EPubHighLightRealmProxy.createUsingJsonStream(realm, reader));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawNote.class)) {
            return clazz.cast(io.realm.DrawNoteRealmProxy.createDetachedCopy((com.hosung.drawpadandepubreader.models.DrawNote) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.UserProfile.class)) {
            return clazz.cast(io.realm.UserProfileRealmProxy.createDetachedCopy((com.hosung.drawpadandepubreader.models.UserProfile) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPoint.class)) {
            return clazz.cast(io.realm.DrawPointRealmProxy.createDetachedCopy((com.hosung.drawpadandepubreader.models.DrawPoint) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.DrawPath.class)) {
            return clazz.cast(io.realm.DrawPathRealmProxy.createDetachedCopy((com.hosung.drawpadandepubreader.models.DrawPath) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.hosung.drawpadandepubreader.models.EPubHighLight.class)) {
            return clazz.cast(io.realm.EPubHighLightRealmProxy.createDetachedCopy((com.hosung.drawpadandepubreader.models.EPubHighLight) realmObject, 0, maxDepth, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

}
