package com.nyasa.synergyfieldengineer.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ChildPojoCase {

    @SerializedName("CaseId") String caseId;
    @SerializedName("CaseNo") String caseNo;
    @SerializedName("caseStatus") String caseStatus;
    @SerializedName("isActive") String isActive;
    @SerializedName("inwardDate") String inwardDate;
    @SerializedName("caseType") String caseType;
    @SerializedName("fileNo") String fileNo;
    @SerializedName("consultantRef") String consultantRef;
    @SerializedName("ClientName") String clientName;
    @SerializedName("ClientContactNo") String ClientContactNo;

    @SerializedName("instituteId") String instituteId;
    @SerializedName("instituteWiseType") String instituteWiseType;
    @SerializedName("propertyType") String propertyType;
    @SerializedName("locality") String locality;
    @SerializedName("SubLocatlity") String subLocality;
    @SerializedName("propertyNo") String propertyNo;
    @SerializedName("floorNo") String floorNo;
    @SerializedName("ProjectName") String projectName;
    @SerializedName("buildingWing") String buildingWing;
    @SerializedName("societyApartmentName") String societyApartmentName;
    @SerializedName("surveyPlotNo") String surveyPlotNo;
    @SerializedName("remainingAddress") String remainingAddress;
    @SerializedName("villageCity") String villageCity;
    @SerializedName("villageCityOld") String  villageCityOld;
    @SerializedName("taluka") String taluka;
    @SerializedName("talukaOld") String talukaOld;
    @SerializedName("district") String district;
    @SerializedName("districtOld") String districtOld;
    @SerializedName("Pincode") String pincode;
    @SerializedName("State") String state;
    @SerializedName("CountryId") String countryId;
    @SerializedName("StateId") String stateId;
    @SerializedName("DistrictId") String districtId;
    @SerializedName("TalukaId") String talukaId;
    @SerializedName("VillageCityId") String villageCityId;
    @SerializedName("OwnerNames") String ownerNames;
    @SerializedName("OwnershipType") String ownershipType;
    private Object previousCaseNo;
    private Object previousCaseId;
    private Object nextActiveCaseId;
    private Object agreementValueRs;
    private Object agreementDate;
    @SerializedName("createdBy") String createdBy;
    @SerializedName("createdOn") String createdOn;
    @SerializedName("lastUpdatedBy") String lastUpdatedBy;
    @SerializedName("lastUpdatedOn") String lastUpdatedOn;
    @SerializedName("approved") String approved;
    private Object preApprovedOn;
    private Object approvedBy;
    private Object approvedOn;
    private Object amendmentNo;
    private Object amendmentNote;
    private Object addressRemarkSV;
    private Object processingComplete;
    @SerializedName("inwardRemark") String inwardRemark;
    @SerializedName("currentOwner") String currentOwner;
    private Object caseStatusPriorHold;
    private Object holdDate;
    private Object holdRevokedDate;
    private Object valuationPurpose;
    private Object printDate;
    private Object printedBy;
    @SerializedName("billGenerated") String billGenerated;
    private Object orgCaseStatus;
    @SerializedName("dispatchStatusManual") String dispatchStatusManual;
    @SerializedName("reportDateManual") String reportDateManual;
    private Object reportDate;
    private Object reportFloorwiseRate;
    @SerializedName("areaUOMPair") String areaUOMPair;
    private Object showSignatureOnReport;
    @SerializedName("LocationId") String locationId;
    private Object preApprovedBy;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getInwardDate() {
        return inwardDate;
    }

    public void setInwardDate(String inwardDate) {
        this.inwardDate = inwardDate;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public Object getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public Object getConsultantRef() {
        return consultantRef;
    }

    public void setConsultantRef(String consultantRef) {
        this.consultantRef = consultantRef;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientContactNo() {
        return ClientContactNo;
    }

    public void setClientContactNo(String clientContactNo) {
        ClientContactNo = clientContactNo;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public Object getInstituteWiseType() {
        return instituteWiseType;
    }

    public void setInstituteWiseType(String instituteWiseType) {
        this.instituteWiseType = instituteWiseType;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getSubLocality() {
        return subLocality;
    }

    public void setSubLocality(String subLocality) {
        this.subLocality = subLocality;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuildingWing() {
        return buildingWing;
    }

    public void setBuildingWing(String buildingWing) {
        this.buildingWing = buildingWing;
    }

    public Object getSocietyApartmentName() {
        return societyApartmentName;
    }

    public void setSocietyApartmentName(String societyApartmentName) {
        this.societyApartmentName = societyApartmentName;
    }

    public String getSurveyPlotNo() {
        return surveyPlotNo;
    }

    public void setSurveyPlotNo(String surveyPlotNo) {
        this.surveyPlotNo = surveyPlotNo;
    }

    public Object getRemainingAddress() {
        return remainingAddress;
    }

    public void setRemainingAddress(String remainingAddress) {
        this.remainingAddress = remainingAddress;
    }

    public String getVillageCity() {
        return villageCity;
    }

    public void setVillageCity(String villageCity) {
        this.villageCity = villageCity;
    }

    public Object getVillageCityOld() {
        return villageCityOld;
    }

    public void setVillageCityOld(String villageCityOld) {
        this.villageCityOld = villageCityOld;
    }

    public Object getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public Object getTalukaOld() {
        return talukaOld;
    }

    public void setTalukaOld(String talukaOld) {
        this.talukaOld = talukaOld;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Object getDistrictOld() {
        return districtOld;
    }

    public void setDistrictOld(String districtOld) {
        this.districtOld = districtOld;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(String talukaId) {
        this.talukaId = talukaId;
    }

    public String getVillageCityId() {
        return villageCityId;
    }

    public void setVillageCityId(String villageCityId) {
        this.villageCityId = villageCityId;
    }

    public String getOwnerNames() {
        return ownerNames;
    }

    public void setOwnerNames(String ownerNames) {
        this.ownerNames = ownerNames;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public Object getPreviousCaseNo() {
        return previousCaseNo;
    }

    public void setPreviousCaseNo(Object previousCaseNo) {
        this.previousCaseNo = previousCaseNo;
    }

    public Object getPreviousCaseId() {
        return previousCaseId;
    }

    public void setPreviousCaseId(Object previousCaseId) {
        this.previousCaseId = previousCaseId;
    }

    public Object getNextActiveCaseId() {
        return nextActiveCaseId;
    }

    public void setNextActiveCaseId(Object nextActiveCaseId) {
        this.nextActiveCaseId = nextActiveCaseId;
    }

    public Object getAgreementValueRs() {
        return agreementValueRs;
    }

    public void setAgreementValueRs(Object agreementValueRs) {
        this.agreementValueRs = agreementValueRs;
    }

    public Object getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(Object agreementDate) {
        this.agreementDate = agreementDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public Object getPreApprovedOn() {
        return preApprovedOn;
    }

    public void setPreApprovedOn(Object preApprovedOn) {
        this.preApprovedOn = preApprovedOn;
    }

    public Object getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Object approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Object getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(Object approvedOn) {
        this.approvedOn = approvedOn;
    }

    public Object getAmendmentNo() {
        return amendmentNo;
    }

    public void setAmendmentNo(Object amendmentNo) {
        this.amendmentNo = amendmentNo;
    }

    public Object getAmendmentNote() {
        return amendmentNote;
    }

    public void setAmendmentNote(Object amendmentNote) {
        this.amendmentNote = amendmentNote;
    }

    public Object getAddressRemarkSV() {
        return addressRemarkSV;
    }

    public void setAddressRemarkSV(Object addressRemarkSV) {
        this.addressRemarkSV = addressRemarkSV;
    }

    public Object getProcessingComplete() {
        return processingComplete;
    }

    public void setProcessingComplete(Object processingComplete) {
        this.processingComplete = processingComplete;
    }

    public String getInwardRemark() {
        return inwardRemark;
    }

    public void setInwardRemark(String inwardRemark) {
        this.inwardRemark = inwardRemark;
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public Object getCaseStatusPriorHold() {
        return caseStatusPriorHold;
    }

    public void setCaseStatusPriorHold(Object caseStatusPriorHold) {
        this.caseStatusPriorHold = caseStatusPriorHold;
    }

    public Object getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Object holdDate) {
        this.holdDate = holdDate;
    }

    public Object getHoldRevokedDate() {
        return holdRevokedDate;
    }

    public void setHoldRevokedDate(Object holdRevokedDate) {
        this.holdRevokedDate = holdRevokedDate;
    }

    public Object getValuationPurpose() {
        return valuationPurpose;
    }

    public void setValuationPurpose(Object valuationPurpose) {
        this.valuationPurpose = valuationPurpose;
    }

    public Object getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Object printDate) {
        this.printDate = printDate;
    }

    public Object getPrintedBy() {
        return printedBy;
    }

    public void setPrintedBy(Object printedBy) {
        this.printedBy = printedBy;
    }

    public String getBillGenerated() {
        return billGenerated;
    }

    public void setBillGenerated(String billGenerated) {
        this.billGenerated = billGenerated;
    }

    public Object getOrgCaseStatus() {
        return orgCaseStatus;
    }

    public void setOrgCaseStatus(Object orgCaseStatus) {
        this.orgCaseStatus = orgCaseStatus;
    }

    public String getDispatchStatusManual() {
        return dispatchStatusManual;
    }

    public void setDispatchStatusManual(String dispatchStatusManual) {
        this.dispatchStatusManual = dispatchStatusManual;
    }

    public String getReportDateManual() {
        return reportDateManual;
    }

    public void setReportDateManual(String reportDateManual) {
        this.reportDateManual = reportDateManual;
    }

    public Object getReportDate() {
        return reportDate;
    }

    public void setReportDate(Object reportDate) {
        this.reportDate = reportDate;
    }

    public Object getReportFloorwiseRate() {
        return reportFloorwiseRate;
    }

    public void setReportFloorwiseRate(Object reportFloorwiseRate) {
        this.reportFloorwiseRate = reportFloorwiseRate;
    }

    public String getAreaUOMPair() {
        return areaUOMPair;
    }

    public void setAreaUOMPair(String areaUOMPair) {
        this.areaUOMPair = areaUOMPair;
    }

    public Object getShowSignatureOnReport() {
        return showSignatureOnReport;
    }

    public void setShowSignatureOnReport(Object showSignatureOnReport) {
        this.showSignatureOnReport = showSignatureOnReport;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Object getPreApprovedBy() {
        return preApprovedBy;
    }

    public void setPreApprovedBy(Object preApprovedBy) {
        this.preApprovedBy = preApprovedBy;
    }


}