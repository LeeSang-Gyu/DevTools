package com.ssteam.nolcam.generic;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Camping {

    public Camping(){}

    public Camping(int db_id, String id, String facltNm, String addr1, String addr2, String firstImageURL, String date) {
        this.db_id = db_id;
        this.id = id;
        this.facltNm = facltNm;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.firstImageURL = firstImageURL;
        this.date = date;
    }
    public Camping(String id, String contentId, String addr1, String facltNm, String firstImageUrl, String induty, double mapX, double mapY, double distance){
        this.id = contentId+"";
        this.contentId = contentId;
        this.addr1 = addr1;
        this.facltNm = facltNm;
        this.firstImageURL = firstImageUrl;
        this.induty = induty;
        this.mapX = mapX;
        this.mapY = mapY;
        this.distance = distance;
    }

    int id2;

    public Camping(int id2, String contentId, String name, String addr1, String addr2, String firstImageUrl, String induty, String lineIntro, int favorites){
        this.id = contentId+"";
        this.facltNm = name;
        this.contentId = contentId;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.firstImageURL = firstImageUrl;
        this.induty = induty;
        this.lineIntro = lineIntro;
        this.favorites = favorites;
    }

    public Camping(int id2, String contentId, String name, String addr1, String addr2, String firstImageUrl, String induty, String lineIntro){
        this.id = contentId+"";
        this.facltNm = name;
        this.contentId = contentId;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.firstImageURL = firstImageUrl;
        this.induty = induty;
        this.lineIntro = lineIntro;
    }

    public Camping(String id, String contentId, String createdtime, String facltNm, String lineIntro, String intro, String featureNm, String induty, String firstImageURL, String allar, String lctCl, String addr1, String addr2, String tel, String homepage, String resveUrl, String resveCl, String exprnProgrm, String posblFcltyCl, String sbrsCl, double mapX, double mapY, String direction, String caravSiteCo, String glampSiteCo, String indvdlCaravSiteCo, String siteMg1Width, String siteMg2Width, String siteMg3Width, String siteMg1Vrticl, String siteMg2Vrticl, String siteMg3Vrticl, String siteMg1Co, String siteMg2Co, String siteMg3Co, String siteBottomCl1, String siteBottomCl2, String siteBottomCl3, String siteBottomCl4, String siteBottomCl5, String tooltip, String glampInnerFclty, String caravInnerFclty, String operPdCl, String operDeCl, String trlerAcmpnyAt, String caravAcmpnyAt, String toiletCo, String swrmCo, String wtrplCo,  int extshrCo, int frprvtWrppCo, int frprvtSandCo, int fireSensorCo, String modifiedtime, String insrncAt, String trsagntNo, String bizrno, String mgcDiv, String manageSttus, String hvofBgnde, String hvofEnddle, String doNm, String sigunguNm, String zipcode, String manageNmpr, String mangeDivNm, String gnrlSiteCo, String autoSiteCo, String sitedStnc, String prmisnDe, String brazierCl, String sbrsEtc, String clturEventAt, String clturEvent, String exprnProgrmAt, String themaEnvrnCl, String eqpmnLendCl, String animalCmgCl, String tourEraCl) {
        this.id = id;
        this.contentId = contentId;
        this.createdtime = createdtime;
        this.facltNm = facltNm;
        this.lineIntro = lineIntro;
        this.intro = intro;
        this.featureNm = featureNm;
        this.induty = induty;
        this.firstImageURL = firstImageURL;
        this.allar = allar;
        this.lctCl = lctCl;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.tel = tel;
        this.homepage = homepage;
        this.resveUrl = resveUrl;
        this.resveCl = resveCl;
        this.exprnProgrm = exprnProgrm;
        this.posblFcltyCl = posblFcltyCl;
        this.sbrsCl = sbrsCl;
        this.mapX = mapX;
        this.mapY = mapY;
        this.direction = direction;
        this.caravSiteCo = caravSiteCo;
        this.glampSiteCo = glampSiteCo;
        this.indvdlCaravSiteCo = indvdlCaravSiteCo;
        this.siteMg1Width = siteMg1Width;
        this.siteMg2Width = siteMg2Width;
        this.siteMg3Width = siteMg3Width;
        this.siteMg1Vrticl = siteMg1Vrticl;
        this.siteMg2Vrticl = siteMg2Vrticl;
        this.siteMg3Vrticl = siteMg3Vrticl;
        this.siteMg1Co = siteMg1Co;
        this.siteMg2Co = siteMg2Co;
        this.siteMg3Co = siteMg3Co;
        this.siteBottomCl1 = siteBottomCl1;
        this.siteBottomCl2 = siteBottomCl2;
        this.siteBottomCl3 = siteBottomCl3;
        this.siteBottomCl4 = siteBottomCl4;
        this.siteBottomCl5 = siteBottomCl5;
        this.tooltip = tooltip;
        this.glampInnerFclty = glampInnerFclty;
        this.caravInnerFclty = caravInnerFclty;
        this.operPdCl = operPdCl;
        this.operDeCl = operDeCl;
        this.trlerAcmpnyAt = trlerAcmpnyAt;
        this.caravAcmpnyAt = caravAcmpnyAt;
        this.toiletCo = toiletCo;
        this.swrmCo = swrmCo;
        this.wtrplCo = wtrplCo;
        this.extshrCo = extshrCo;
        this.frprvtWrppCo = frprvtWrppCo;
        this.frprvtSandCo = frprvtSandCo;
        this.fireSensorCo = fireSensorCo;
        this.modifiedtime = modifiedtime;
        this.insrncAt = insrncAt;
        this.trsagntNo = trsagntNo;
        this.bizrno = bizrno;
        this.mgcDiv = mgcDiv;
        this.manageSttus = manageSttus;
        this.hvofBgnde = hvofBgnde;
        this.hvofEnddle = hvofEnddle;
        this.doNm = doNm;
        this.sigunguNm = sigunguNm;
        this.zipcode = zipcode;
        this.manageNmpr = manageNmpr;
        this.mangeDivNm = mangeDivNm;
        this.gnrlSiteCo = gnrlSiteCo;
        this.autoSiteCo = autoSiteCo;
        this.sitedStnc = sitedStnc;
        this.prmisnDe = prmisnDe;
        this.brazierCl = brazierCl;
        this.sbrsEtc = sbrsEtc;
        this.clturEventAt = clturEventAt;
        this.clturEvent = clturEvent;
        this.exprnProgrmAt = exprnProgrmAt;
        this.themaEnvrnCl = themaEnvrnCl;
        this.eqpmnLendCl = eqpmnLendCl;
        this.animalCmgCl = animalCmgCl;
        this.tourEraCl = tourEraCl;
    }


    public int db_id;
    public String date;

    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함
    @SerializedName("id")
    public String id;
    @SerializedName("contentId")
    public String contentId;
    @SerializedName("createdtime")
    public String createdtime;
    @SerializedName("facltNm")
    public String facltNm;
    @SerializedName("lineIntro")
    public String lineIntro;
    @SerializedName("intro")
    public String intro;
    @SerializedName("featureNm")
    public String featureNm;
    @SerializedName("induty")
    public String induty;
    @SerializedName("firstImageUrl")
    public String firstImageURL;

    @SerializedName("allar")
    public String allar;
    @SerializedName("lctCl")
    public String lctCl;
    @SerializedName("addr1")
    public String addr1;
    @SerializedName("addr2")
    public String addr2;
    @SerializedName("tel")
    public String tel;
    @SerializedName("homepage")
    public String homepage;
    @SerializedName("resveUrl")
    public String resveUrl;
    @SerializedName("resveCl")
    public String resveCl;
    @SerializedName("exprnProgrm")
    public String exprnProgrm;
    @SerializedName("posblFcltyCl")
    public String posblFcltyCl;
    @SerializedName("sbrsCl")
    public String sbrsCl;
    @SerializedName("mapX")
    public double mapX;
    @SerializedName("mapY")
    public double mapY;

    @SerializedName("direction")
    public String direction;
    @SerializedName("caravSiteCo")
    public String caravSiteCo;
    @SerializedName("glampSiteCo")
    public String glampSiteCo;
    @SerializedName("indvdlCaravSiteCo")
    public String indvdlCaravSiteCo;
    @SerializedName("siteMg1Width")
    public String siteMg1Width;
    @SerializedName("siteMg2Width")
    public String siteMg2Width;
    @SerializedName("siteMg3Width")
    public String siteMg3Width;
    @SerializedName("siteMg1Vrticl")
    public String siteMg1Vrticl;
    @SerializedName("siteMg2Vrticl")
    public String siteMg2Vrticl;
    @SerializedName("siteMg3Vrticl")
    public String siteMg3Vrticl;
    @SerializedName("siteMg1Co")
    public String siteMg1Co;
    @SerializedName("siteMg2Co")
    public String siteMg2Co;
    @SerializedName("siteMg3Co")
    public String siteMg3Co;
    @SerializedName("siteBottomCl1")
    public String siteBottomCl1;
    @SerializedName("siteBottomCl2")
    public String siteBottomCl2;
    @SerializedName("siteBottomCl3")
    public String siteBottomCl3;
    @SerializedName("siteBottomCl4")
    public String siteBottomCl4;
    @SerializedName("siteBottomCl5")
    public String siteBottomCl5;
    @SerializedName("tooltip")
    public String tooltip;
    @SerializedName("glampInnerFclty")
    public String glampInnerFclty;
    @SerializedName("caravInnerFclty")
    public String caravInnerFclty;
    @SerializedName("operPdCl")
    public String operPdCl;
    @SerializedName("operDeCl")
    public String operDeCl;
    @SerializedName("trlerAcmpnyAt")
    public String trlerAcmpnyAt;
    @SerializedName("caravAcmpnyAt")
    public String caravAcmpnyAt;
    @SerializedName("toiletCo")
    public String toiletCo;
    @SerializedName("swrmCo")
    public String swrmCo;
    @SerializedName("wtrplCo")
    public String wtrplCo;

    @SerializedName("weekCount")
    public String weekCount;

    @SerializedName("monthCount")
    public String monthCount;

    @SerializedName("yearCount")
    public String yearCount;
    @SerializedName("distance")
    public double distance;

    @SerializedName("extshrCo")
    public int extshrCo;

    @SerializedName("frprvtWrppCo")
    public int frprvtWrppCo;

    @SerializedName("frprvtSandCo")
    public int frprvtSandCo;

    @SerializedName("fireSensorCo")
    public int fireSensorCo;


    @SerializedName("favorites")
    public int favorites;

    @SerializedName("modifiedtime")
    @Expose
    public String modifiedtime;       //수정일




    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }
    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }
    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    @SerializedName("syncStatus")
    @Expose
    public String syncStatus;  //업데이트 구분


    @SerializedName("insrncAt")
    public String insrncAt;

    @SerializedName("trsagntNo")
    public String trsagntNo;

    @SerializedName("bizrno")
    public String bizrno;

    @SerializedName("mgcDiv")
    public String mgcDiv;

    @SerializedName("manageSttus")
    public String manageSttus;

    @SerializedName("hvofBgnde")
    public String hvofBgnde;

    @SerializedName("hvofEnddle")
    public String hvofEnddle;

    @SerializedName("doNm")
    public String doNm;

    @SerializedName("sigunguNm")
    public String sigunguNm;

    @SerializedName("zipcode")
    public String zipcode;

    @SerializedName("manageNmpr")
    public String manageNmpr;

    @SerializedName("mangeDivNm")
    public String mangeDivNm;

    @SerializedName("gnrlSiteCo")
    public String gnrlSiteCo;

    @SerializedName("autoSiteCo")
    public String autoSiteCo;

    @SerializedName("sitedStnc")
    public String sitedStnc;

    @SerializedName("prmisnDe")
    public String prmisnDe;

    @SerializedName("brazierCl")
    public String brazierCl;

    @SerializedName("sbrsEtc")
    public String sbrsEtc;

    @SerializedName("clturEventAt")
    public String clturEventAt;

    @SerializedName("clturEvent")
    public String clturEvent;

    @SerializedName("exprnProgrmAt")
    public String exprnProgrmAt;

    @SerializedName("themaEnvrnCl")
    public String themaEnvrnCl;

    @SerializedName("eqpmnLendCl")
    public String eqpmnLendCl;

    @SerializedName("animalCmgCl")
    public String animalCmgCl;

    @SerializedName("tourEraCl")
    public String tourEraCl;

    // toString()을 Override 해주지 않으면 객체 주소값을 출력
    @Override
    public String toString() {
        return "Camping{" +
                "db_id=" + db_id +
                ", date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", contentId='" + contentId + '\'' +
                ", createdtime='" + createdtime + '\'' +
                ", facltNm='" + facltNm + '\'' +
                ", lineIntro='" + lineIntro + '\'' +
                ", intro='" + intro + '\'' +
                ", featureNm='" + featureNm + '\'' +
                ", induty='" + induty + '\'' +
                ", firstImageURL='" + firstImageURL + '\'' +
                ", lctCl='" + lctCl + '\'' +
                ", addr1='" + addr1 + '\'' +
                ", addr2='" + addr2 + '\'' +
                ", tel='" + tel + '\'' +
                ", homepage='" + homepage + '\'' +
                ", resveUrl='" + resveUrl + '\'' +
                ", resveCl='" + resveCl + '\'' +
                ", exprnProgrm='" + exprnProgrm + '\'' +
                ", posblFcltyCl='" + posblFcltyCl + '\'' +
                ", sbrsCl='" + sbrsCl + '\'' +
                ", mapX='" + mapX + '\'' +
                ", mapY='" + mapY + '\'' +
                ", caravSiteCo='" + caravSiteCo + '\'' +
                ", glampSiteCo='" + glampSiteCo + '\'' +
                ", indvdlCaravSiteCo='" + indvdlCaravSiteCo + '\'' +
                ", siteMg1Width='" + siteMg1Width + '\'' +
                ", siteMg2Width='" + siteMg2Width + '\'' +
                ", siteMg3Width='" + siteMg3Width + '\'' +
                ", siteMg1Vrticl='" + siteMg1Vrticl + '\'' +
                ", siteMg2Vrticl='" + siteMg2Vrticl + '\'' +
                ", siteMg3Vrticl='" + siteMg3Vrticl + '\'' +
                ", siteMg1Co='" + siteMg1Co + '\'' +
                ", siteMg2Co='" + siteMg2Co + '\'' +
                ", siteMg3Co='" + siteMg3Co + '\'' +
                ", siteBottomCl1='" + siteBottomCl1 + '\'' +
                ", siteBottomCl2='" + siteBottomCl2 + '\'' +
                ", siteBottomCl3='" + siteBottomCl3 + '\'' +
                ", siteBottomCl4='" + siteBottomCl4 + '\'' +
                ", siteBottomCl5='" + siteBottomCl5 + '\'' +
                ", tooltip='" + tooltip + '\'' +
                ", glampInnerFclty='" + glampInnerFclty + '\'' +
                ", caravInnerFclty='" + caravInnerFclty + '\'' +
                ", operPdCl='" + operPdCl + '\'' +
                ", operDeCl='" + operDeCl + '\'' +
                ", trlerAcmpnyAt='" + trlerAcmpnyAt + '\'' +
                ", caravAcmpnyAt='" + caravAcmpnyAt + '\'' +
                ", toiletCo='" + toiletCo + '\'' +
                ", swrmCo='" + swrmCo + '\'' +
                ", wtrplCo='" + wtrplCo + '\'' +
                ", weekCount='" + weekCount + '\'' +
                ", monthCount='" + monthCount + '\'' +
                ", yearCount='" + yearCount + '\'' +
                ", distance=" + distance +
                ", extshrCo=" + extshrCo +
                ", frprvtWrppCo=" + frprvtWrppCo +
                ", frprvtSandCo=" + frprvtSandCo +
                ", fireSensorCo=" + fireSensorCo +
                '}';
    }

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getFacltNm() {
        return facltNm;
    }

    public void setFacltNm(String facltNm) {
        this.facltNm = facltNm;
    }

    public String getLineIntro() {
        return lineIntro;
    }

    public void setLineIntro(String lineIntro) {
        this.lineIntro = lineIntro;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getFeatureNm() {
        return featureNm;
    }

    public void setFeatureNm(String featureNm) {
        this.featureNm = featureNm;
    }

    public String getInduty() {
        return induty;
    }

    public void setInduty(String induty) {
        this.induty = induty;
    }

    public String getFirstImageURL() {
        return firstImageURL;
    }

    public void setFirstImageURL(String firstImageURL) {
        this.firstImageURL = firstImageURL;
    }

    public String getLctCl() {
        return lctCl;
    }

    public void setLctCl(String lctCl) {
        this.lctCl = lctCl;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getResveUrl() {
        return resveUrl;
    }

    public void setResveUrl(String resveUrl) {
        this.resveUrl = resveUrl;
    }

    public String getResveCl() {
        return resveCl;
    }

    public void setResveCl(String resveCl) {
        this.resveCl = resveCl;
    }

    public String getExprnProgrm() {
        return exprnProgrm;
    }

    public void setExprnProgrm(String exprnProgrm) {
        this.exprnProgrm = exprnProgrm;
    }

    public String getPosblFcltyCl() {
        return posblFcltyCl;
    }

    public void setPosblFcltyCl(String posblFcltyCl) {
        this.posblFcltyCl = posblFcltyCl;
    }

    public String getSbrsCl() {
        return sbrsCl;
    }

    public void setSbrsCl(String sbrsCl) {
        this.sbrsCl = sbrsCl;
    }

    public String getAllar() {
        return allar;
    }

    public void setAllar(String allar) {
        this.allar = allar;
    }


    public double getMapX() {
        return mapX;
    }

    public void setMapX(double mapX) {
        this.mapX = mapX;
    }

    public double getMapY() {
        return mapY;
    }

    public void setMapY(double mapY) {
        this.mapY = mapY;
    }

    public String getCaravSiteCo() {
        return caravSiteCo;
    }

    public void setCaravSiteCo(String caravSiteCo) {
        this.caravSiteCo = caravSiteCo;
    }

    public String getGlampSiteCo() {
        return glampSiteCo;
    }

    public void setGlampSiteCo(String glampSiteCo) {
        this.glampSiteCo = glampSiteCo;
    }

    public String getIndvdlCaravSiteCo() {
        return indvdlCaravSiteCo;
    }

    public void setIndvdlCaravSiteCo(String indvdlCaravSiteCo) {
        this.indvdlCaravSiteCo = indvdlCaravSiteCo;
    }

    public String getSiteMg1Width() {
        return siteMg1Width;
    }

    public void setSiteMg1Width(String siteMg1Width) {
        this.siteMg1Width = siteMg1Width;
    }

    public String getSiteMg2Width() {
        return siteMg2Width;
    }

    public void setSiteMg2Width(String siteMg2Width) {
        this.siteMg2Width = siteMg2Width;
    }

    public String getSiteMg3Width() {
        return siteMg3Width;
    }

    public void setSiteMg3Width(String siteMg3Width) {
        this.siteMg3Width = siteMg3Width;
    }

    public String getSiteMg1Vrticl() {
        return siteMg1Vrticl;
    }

    public void setSiteMg1Vrticl(String siteMg1Vrticl) {
        this.siteMg1Vrticl = siteMg1Vrticl;
    }

    public String getSiteMg2Vrticl() {
        return siteMg2Vrticl;
    }

    public void setSiteMg2Vrticl(String siteMg2Vrticl) {
        this.siteMg2Vrticl = siteMg2Vrticl;
    }

    public String getSiteMg3Vrticl() {
        return siteMg3Vrticl;
    }

    public void setSiteMg3Vrticl(String siteMg3Vrticl) {
        this.siteMg3Vrticl = siteMg3Vrticl;
    }

    public String getSiteMg1Co() {
        return siteMg1Co;
    }

    public void setSiteMg1Co(String siteMg1Co) {
        this.siteMg1Co = siteMg1Co;
    }

    public String getSiteMg2Co() {
        return siteMg2Co;
    }

    public void setSiteMg2Co(String siteMg2Co) {
        this.siteMg2Co = siteMg2Co;
    }

    public String getSiteMg3Co() {
        return siteMg3Co;
    }

    public void setSiteMg3Co(String siteMg3Co) {
        this.siteMg3Co = siteMg3Co;
    }

    public String getSiteBottomCl1() {
        return siteBottomCl1;
    }

    public void setSiteBottomCl1(String siteBottomCl1) {
        this.siteBottomCl1 = siteBottomCl1;
    }

    public String getSiteBottomCl2() {
        return siteBottomCl2;
    }

    public void setSiteBottomCl2(String siteBottomCl2) {
        this.siteBottomCl2 = siteBottomCl2;
    }

    public String getSiteBottomCl3() {
        return siteBottomCl3;
    }

    public void setSiteBottomCl3(String siteBottomCl3) {
        this.siteBottomCl3 = siteBottomCl3;
    }

    public String getSiteBottomCl4() {
        return siteBottomCl4;
    }

    public void setSiteBottomCl4(String siteBottomCl4) {
        this.siteBottomCl4 = siteBottomCl4;
    }

    public String getSiteBottomCl5() {
        return siteBottomCl5;
    }

    public void setSiteBottomCl5(String siteBottomCl5) {
        this.siteBottomCl5 = siteBottomCl5;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getGlampInnerFclty() {
        return glampInnerFclty;
    }

    public void setGlampInnerFclty(String glampInnerFclty) {
        this.glampInnerFclty = glampInnerFclty;
    }

    public String getCaravInnerFclty() {
        return caravInnerFclty;
    }

    public void setCaravInnerFclty(String caravInnerFclty) {
        this.caravInnerFclty = caravInnerFclty;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOperPdCl() {
        return operPdCl;
    }

    public void setOperPdCl(String operPdCl) {
        this.operPdCl = operPdCl;
    }

    public String getOperDeCl() {
        return operDeCl;
    }

    public void setOperDeCl(String operDeCl) {
        this.operDeCl = operDeCl;
    }

    public String getTrlerAcmpnyAt() {
        return trlerAcmpnyAt;
    }

    public void setTrlerAcmpnyAt(String trlerAcmpnyAt) {
        this.trlerAcmpnyAt = trlerAcmpnyAt;
    }

    public String getCaravAcmpnyAt() {
        return caravAcmpnyAt;
    }

    public void setCaravAcmpnyAt(String caravAcmpnyAt) {
        this.caravAcmpnyAt = caravAcmpnyAt;
    }

    public String getToiletCo() {
        return toiletCo;
    }

    public void setToiletCo(String toiletCo) {
        this.toiletCo = toiletCo;
    }

    public String getSwrmCo() {
        return swrmCo;
    }

    public void setSwrmCo(String swrmCo) {
        this.swrmCo = swrmCo;
    }

    public String getWtrplCo() {
        return wtrplCo;
    }

    public void setWtrplCo(String wtrplCo) {
        this.wtrplCo = wtrplCo;
    }

    public String getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(String weekCount) {
        this.weekCount = weekCount;
    }

    public String getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(String monthCount) {
        this.monthCount = monthCount;
    }

    public String getYearCount() {
        return yearCount;
    }

    public void setYearCount(String yearCount) {
        this.yearCount = yearCount;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getExtshrCo() {
        return extshrCo;
    }

    public void setExtshrCo(int extshrCo) {
        this.extshrCo = extshrCo;
    }

    public int getFrprvtWrppCo() {
        return frprvtWrppCo;
    }

    public void setFrprvtWrppCo(int frprvtWrppCo) {
        this.frprvtWrppCo = frprvtWrppCo;
    }

    public int getFrprvtSandCo() {
        return frprvtSandCo;
    }

    public void setFrprvtSandCo(int frprvtSandCo) {
        this.frprvtSandCo = frprvtSandCo;
    }

    public int getFireSensorCo() {
        return fireSensorCo;
    }

    public void setFireSensorCo(int fireSensorCo) {
        this.fireSensorCo = fireSensorCo;
    }

    public String getInsrncAt() {
        return insrncAt;
    }

    public void setInsrncAt(String insrncAt) {
        this.insrncAt = insrncAt;
    }

    public String getTrsagntNo() {
        return trsagntNo;
    }

    public void setTrsagntNo(String trsagntNo) {
        this.trsagntNo = trsagntNo;
    }

    public String getBizrno() {
        return bizrno;
    }

    public void setBizrno(String bizrno) {
        this.bizrno = bizrno;
    }

    public String getMgcDiv() {
        return mgcDiv;
    }

    public void setMgcDiv(String mgcDiv) {
        this.mgcDiv = mgcDiv;
    }

    public String getManageSttus() {
        return manageSttus;
    }

    public void setManageSttus(String manageSttus) {
        this.manageSttus = manageSttus;
    }

    public String getHvofBgnde() {
        return hvofBgnde;
    }

    public void setHvofBgnde(String hvofBgnde) {
        this.hvofBgnde = hvofBgnde;
    }

    public String getHvofEnddle() {
        return hvofEnddle;
    }

    public void setHvofEnddle(String hvofEnddle) {
        this.hvofEnddle = hvofEnddle;
    }

    public String getDoNm() {
        return doNm;
    }

    public void setDoNm(String doNm) {
        this.doNm = doNm;
    }

    public String getSigunguNm() {
        return sigunguNm;
    }

    public void setSigunguNm(String sigunguNm) {
        this.sigunguNm = sigunguNm;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getManageNmpr() {
        return manageNmpr;
    }

    public void setManageNmpr(String manageNmpr) {
        this.manageNmpr = manageNmpr;
    }

    public String getMangeDivNm() {
        return mangeDivNm;
    }

    public void setMangeDivNm(String mangeDivNm) {
        this.mangeDivNm = mangeDivNm;
    }

    public String getGnrlSiteCo() {
        return gnrlSiteCo;
    }

    public void setGnrlSiteCo(String gnrlSiteCo) {
        this.gnrlSiteCo = gnrlSiteCo;
    }

    public String getAutoSiteCo() {
        return autoSiteCo;
    }

    public void setAutoSiteCo(String autoSiteCo) {
        this.autoSiteCo = autoSiteCo;
    }

    public String getSitedStnc() {
        return sitedStnc;
    }

    public void setSitedStnc(String sitedStnc) {
        this.sitedStnc = sitedStnc;
    }

    public String getPrmisnDe() {
        return prmisnDe;
    }

    public void setPrmisnDe(String prmisnDe) {
        this.prmisnDe = prmisnDe;
    }

    public String getBrazierCl() {
        return brazierCl;
    }

    public void setBrazierCl(String brazierCl) {
        this.brazierCl = brazierCl;
    }

    public String getSbrsEtc() {
        return sbrsEtc;
    }

    public void setSbrsEtc(String sbrsEtc) {
        this.sbrsEtc = sbrsEtc;
    }

    public String getClturEventAt() {
        return clturEventAt;
    }

    public void setClturEventAt(String clturEventAt) {
        this.clturEventAt = clturEventAt;
    }

    public String getClturEvent() {
        return clturEvent;
    }

    public void setClturEvent(String clturEvent) {
        this.clturEvent = clturEvent;
    }

    public String getExprnProgrmAt() {
        return exprnProgrmAt;
    }

    public void setExprnProgrmAt(String exprnProgrmAt) {
        this.exprnProgrmAt = exprnProgrmAt;
    }

    public String getThemaEnvrnCl() {
        return themaEnvrnCl;
    }

    public void setThemaEnvrnCl(String themaEnvrnCl) {
        this.themaEnvrnCl = themaEnvrnCl;
    }

    public String getEqpmnLendCl() {
        return eqpmnLendCl;
    }

    public void setEqpmnLendCl(String eqpmnLendCl) {
        this.eqpmnLendCl = eqpmnLendCl;
    }

    public String getAnimalCmgCl() {
        return animalCmgCl;
    }

    public void setAnimalCmgCl(String animalCmgCl) {
        this.animalCmgCl = animalCmgCl;
    }

    public String getTourEraCl() {
        return tourEraCl;
    }

    public void setTourEraCl(String tourEraCl) {
        this.tourEraCl = tourEraCl;
    }

}