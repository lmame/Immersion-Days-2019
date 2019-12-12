package main.java.com.bmc.panama;

public class Incident {
    public String incidentNumber;
    public String summary;
    public String guid;

    public String getIncidentNumber() {
        return incidentNumber;
    }

    public void setIncidentNumber(String incNumber) {
        incidentNumber = incNumber;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String incidentSummary) {
        summary = incidentSummary;
    }

    public String getGUID() {
        return guid;
    }

    public void setGUID(String incidentGuid) {
        this.guid = incidentGuid;
    }
}
