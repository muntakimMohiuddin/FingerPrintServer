
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.util.Base64;

public class RohingaDomainModel {

    private String referenceNo;
    private Date creationDate;
    private String createdBy;
    private Time dateOfEntry;
    private String machineId;
    private String nameEng;
    private String fatherNameEng;
    private String motherNameEng;
    private Date dateOfBirth;
    private String placeOfBirth;
    private String gender;
    private String religion;
    private String nationality;
    private int age;
    private String country;
    private String address;
    private String village;
    private String policeStation;
    private String district;
    private byte [] photo;

    public String getReferenceNo() {
        return referenceNo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setDateOfEntry(Time dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public void setFatherNameEng(String fatherNameEng) {
        this.fatherNameEng = fatherNameEng;
    }

    public void setMotherNameEng(String motherNameEng) {
        this.motherNameEng = motherNameEng;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;//Base64.getDecoder().decode(photo.getBytes());
    }

    public Time getDateOfEntry() {
        return dateOfEntry;
    }

    public String getMachineId() {
        return machineId;
    }

    public String getNameEng() {
        return nameEng;
    }

    public String getFatherNameEng() {
        return fatherNameEng;
    }

    public String getMotherNameEng() {
        return motherNameEng;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getReligion() {
        return religion;
    }

    public String getNationality() {
        return nationality;
    }

    public int getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getVillage() {
        return village;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public String getDistrict() {
        return district;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public RohingaDomainModel(String referenceNo, Date creationDate, String createdBy, Time dateOfEntry, String machineId, String nameEng, String fatherNameEng, String motherNameEng, Date dateOfBirth, String placeOfBirth, String gender, String religion, String nationality, int age, String country, String address, String village, String policeStation, String district, byte[] photo) {
        this.referenceNo = referenceNo;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
        this.dateOfEntry = dateOfEntry;
        this.machineId = machineId;
        this.nameEng = nameEng;
        this.fatherNameEng = fatherNameEng;
        this.motherNameEng = motherNameEng;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.gender = gender;
        this.religion = religion;
        this.nationality = nationality;
        this.age = age;
        this.country = country;
        this.address = address;
        this.village = village;
        this.policeStation = policeStation;
        this.district = district;
        this.photo = photo;//Base64.getDecoder().decode(photo.getBytes());
    }

    public RohingaDomainModel() {
    }
}





