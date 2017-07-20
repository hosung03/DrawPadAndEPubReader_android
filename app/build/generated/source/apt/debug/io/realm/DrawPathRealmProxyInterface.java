package io.realm;


public interface DrawPathRealmProxyInterface {
    public boolean realmGet$saved();
    public void realmSet$saved(boolean value);
    public boolean realmGet$completed();
    public void realmSet$completed(boolean value);
    public String realmGet$color();
    public void realmSet$color(String value);
    public Integer realmGet$bushsize();
    public void realmSet$bushsize(Integer value);
    public RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> realmGet$points();
    public void realmSet$points(RealmList<com.hosung.drawpadandepubreader.models.DrawPoint> value);
}
