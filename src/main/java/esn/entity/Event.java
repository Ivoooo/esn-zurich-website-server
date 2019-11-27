package esn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Event {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String englishText;

    @Column
    private String germanText;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column
    private LocalDateTime creationDate;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private int zip;

    @Column
    private boolean diet; //if infos about vegetarian / .. is required

    //0 means no registration is necessary
    @Column
    private int maxParticipants;

    @Column(nullable = false)
    private int fullPrice;

    @Column(nullable = false)
    private int esnPrice;

    @Column
    private int esnHalbtaxPrice;

    @Column
    private int noEsnHalbtaxPrice;

    @Column
    private int subsidyRequested;

    @Column
    private int subsidyApproved;

    @Column
    private int subsidyUsed;

    @Column
    private String organizors;

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }

    public String getEnglishText() {
        return englishText;
    }

    public void setGermanText(String germanText) {
        this.germanText = germanText;
    }

    public String getGermanText() {
        return germanText;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public final void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setFullPrice(int fullPrice) {
        this.fullPrice = fullPrice;
    }

    public int getFullPrice() {
        return fullPrice;
    }

    public void setEsnPrice(int esnPrice) {
        this.esnPrice = esnPrice;
    }

    public int getEsnPrice() {
        return esnPrice;
    }

    public void setSubsidyApproved(int subsidyApproved) {
        this.subsidyApproved = subsidyApproved;
    }

    public int getSubsidyApproved() {
        return subsidyApproved;
    }

    public void setSubsidyUsed(int subsidyUsed) {
        this.subsidyUsed = subsidyUsed;
    }

    public int getSubsidyUsed() {
        return subsidyUsed;
    }

    public void setSubsidyRequested(int subsidyRequested) {
        this.subsidyRequested = subsidyRequested;
    }

    public int getSubsidyRequested() {
        return subsidyRequested;
    }

    public void setOrganizors(String organizors) {
        this.organizors = organizors;
    }

    public String getOrganizors() {
        return organizors;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public boolean isDiet() {
        return diet;
    }

    public void setDiet(boolean diet) {
        this.diet = diet;
    }

    public int getEsnHalbtaxPrice() {
        return esnHalbtaxPrice;
    }

    public void setEsnHalbtaxPrice(int esnHalbtaxPrice) {
        this.esnHalbtaxPrice = esnHalbtaxPrice;
    }

    public int getNoEsnHalbtaxPrice() {
        return noEsnHalbtaxPrice;
    }

    public void setNoEsnHalbtaxPrice(int noEsnHalbtaxPrice) {
        this.noEsnHalbtaxPrice = noEsnHalbtaxPrice;
    }
}
