package com.lcdt.driver.dto;

public class UploadPointDto {

    private String latitude;

    private String longitude;

    private String entity_name;

    private String loc_time;

    private String ak;

    private String service_id;

    private String coord_type_input;

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getCoord_type_input() {
        return coord_type_input;
    }

    public void setCoord_type_input(String coord_type_input) {
        this.coord_type_input = coord_type_input;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getLoc_time() {
        return loc_time;
    }

    public void setLoc_time(String loc_time) {
        this.loc_time = loc_time;
    }

    @Override
    public String toString() {
        return "UploadPointDto{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", entity_name='" + entity_name + '\'' +
                ", loc_time='" + loc_time + '\'' +
                ", ak='" + ak + '\'' +
                ", service_id='" + service_id + '\'' +
                ", coord_type_input='" + coord_type_input + '\'' +
                '}';
    }
}
