package io.realm;


public interface DrawNoteRealmProxyInterface {
    public int realmGet$id();
    public void realmSet$id(int value);
    public boolean realmGet$saved();
    public void realmSet$saved(boolean value);
    public String realmGet$user();
    public void realmSet$user(String value);
    public String realmGet$title();
    public void realmSet$title(String value);
    public RealmList<com.hosung.drawpadandepubreader.models.DrawPath> realmGet$paths();
    public void realmSet$paths(RealmList<com.hosung.drawpadandepubreader.models.DrawPath> value);
}
