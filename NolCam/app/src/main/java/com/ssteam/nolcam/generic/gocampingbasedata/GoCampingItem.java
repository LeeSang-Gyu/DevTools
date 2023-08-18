/*
* resveCl 예약 구분  -> response 어떻게 오는지 확인
* */


package com.ssteam.nolcam.generic.gocampingbasedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoCampingItem {

    @SerializedName("contentId")
    @Expose
    private String contentId;       //콘텐츠 ID

    @SerializedName("facltNm")
    @Expose
    private String facltNm;     //야영장명

    @SerializedName("lineIntro")
    @Expose
    private String lineIntro;       //한줄소개

    @SerializedName("intro")
    @Expose
    private String intro;       //소개

    @SerializedName("allar")
    @Expose
    private String allar;      //전체면적

    @SerializedName("insrncAt")
    @Expose
    private String insrncAt;       //영업배상책임보험 가입여부(Y:사용, N:미사용)

    @SerializedName("trsagntNo")
    @Expose
    private String trsagntNo;      //관광사업자번호

    @SerializedName("bizrno")
    @Expose
    private String bizrno;     //사업자번호

    @SerializedName("facltDivNm")
    @Expose
    private String facltDivNm;     //사업주체.구분

    @SerializedName("mangeDivNm")
    @Expose
    private String mangeDivNm;     //운영주체.관리주체 (직영, 위탁)

    @SerializedName("mgcDiv")
    @Expose
    private String mgcDiv;         //운영기관.관리기관

    @SerializedName("manageSttus")
    @Expose
    private String manageSttus;        //운영상태.관리상태

    @SerializedName("hvofBgnde")
    @Expose
    private String hvofBgnde;      //휴장기간.휴무기간 시작일

    @SerializedName("hvofEnddle")
    @Expose
    private String hvofEnddle;     //휴장기간.휴무기간 종료일

    @SerializedName("featureNm")
    @Expose
    private String featureNm;      //특징

    @SerializedName("induty")
    @Expose
    private String induty;     //업종

    @SerializedName("lctCl")
    @Expose
    private String lctCl;       //입지구분

    @SerializedName("doNm")
    @Expose
    private String doNm;       //도

    @SerializedName("sigunguNm")
    @Expose
    private String sigunguNm;       //시,군,구

    //-------------------------------------------------
    @SerializedName("zipcode")
    @Expose
    private String zipcode;        //우편번호

    @SerializedName("addr1")
    @Expose
    private  String addr1;      //주소

    @SerializedName("addr2")
    @Expose
    private String addr2;       //상세주소

    @SerializedName("mapX")
    @Expose
    private String mapX;       //경도(X)

    @SerializedName("mapY")
    @Expose
    private String mapY;        //위도(Y)

    @SerializedName("direction")
    @Expose
    private String direction;      //오시는 길 컨텐츠

    //-------------------------------------------------
    @SerializedName("tel")
    @Expose
    private String tel;        //전화

    @SerializedName("homepage")
    @Expose
    private String homepage;        //홈페이지

    @SerializedName("resveUrl")
    @Expose
    private String resveUrl;       //예약 페이지

    @SerializedName("resveCl")
    @Expose
    private String resveCl;       //예약 구분

    @SerializedName("manageNmpr")
    @Expose
    private String manageNmpr;     //상주관리인원

    @SerializedName("gnrlSiteCo")
    @Expose
    private String gnrlSiteCo;     //주요시설 일반야영장

    @SerializedName("autoSiteCo")
    @Expose
    private String autoSiteCo;      //주요시설 자동차야영장

    //-------------------------------------------------
    @SerializedName("glampSiteCo")
    @Expose
    private String glampSiteCo;        //주요시설 글램핑

    @SerializedName("caravSiteCo")
    @Expose
    private String caravSiteCo;        //주요시설 카라반

    @SerializedName("indvdlCaravSiteCo")
    @Expose
    private String indvdlCaravSiteCo;      //주요시설 개인 카라반

    @SerializedName("sitedStnc")
    @Expose
    private String sitedStnc;      //사이트간 거리

    @SerializedName("siteMg1Width")
    @Expose
    private String siteMg1Width;       //사이트 크기1 가로

    @SerializedName("siteMg2Width")
    @Expose
    private String siteMg2Width;       //사이트 크기2 가로

    //-------------------------------------------------
    @SerializedName("siteMg3Width")
    @Expose
    private String siteMg3Width;       //사이트 크기3 가로

    @SerializedName("siteMg1Vrticl")
    @Expose
    private String siteMg1Vrticl;      //사이트 크기1 세로

    @SerializedName("siteMg2Vrticl")
    @Expose
    private String siteMg2Vrticl;      //사이트 크기2 세로

    @SerializedName("siteMg3Vrticl")
    @Expose
    private String siteMg3Vrticl;      //사이트 크기3 세로

    @SerializedName("siteMg1Co")
    @Expose
    private String siteMg1Co;      //사이트 크기1 수량

    @SerializedName("siteMg2Co")
    @Expose
    private String siteMg2Co;      //사이트 크기2 수량

    //-------------------------------------------------
    @SerializedName("siteMg3Co")
    @Expose
    private String siteMg3Co;      //사이트 크기3 수량

    @SerializedName("siteBottomCl1")
    @Expose
    private String siteBottomCl1;      //잔디

    @SerializedName("siteBottomCl2")
    @Expose
    private String siteBottomCl2;      //파쇄석

    @SerializedName("siteBottomCl3")
    @Expose
    private String siteBottomCl3;      //테크

    @SerializedName("siteBottomCl4")
    @Expose
    private String siteBottomCl4;      //자갈

    @SerializedName("siteBottomCl5")
    @Expose
    private String siteBottomCl5;      //맨흙

    //-------------------------------------------------
    @SerializedName("tooltip")
    @Expose
    private String tooltip;        //툴팁

    @SerializedName("glampInnerFclty")
    @Expose
    private String glampInnerFclty;        //글램핑 - 내부시설

    @SerializedName("caravInnerFclty")
    @Expose
    private String caravInnerFclty;        //카라반 - 내부시설

    @SerializedName("prmisnDe")
    @Expose
    private String prmisnDe;       //인허가일자

    @SerializedName("operPdCl")
    @Expose
    private String operPdCl;       //운영기간

    @SerializedName("operDeCl")
    @Expose
    private String operDeCl;       //운영일

    //-------------------------------------------------
    @SerializedName("trlerAcmpnyAt")
    @Expose
    private String trlerAcmpnyAt;      //개인 트레일러 동반 여부(Y:사용, N:미사용)

    @SerializedName("caravAcmpnyAt")
    @Expose
    private String caravAcmpnyAt;      //개인 카라반 동반 여부(Y:사용, N:미사용)

    @SerializedName("toiletCo")
    @Expose
    private String toiletCo;       //화장실 개수

    @SerializedName("swrmCo")
    @Expose
    private String swrmCo;     //샤워실 개수

    @SerializedName("brazierCl")
    @Expose
    private String brazierCl;    //화로대

    @SerializedName("sbrsCl")
    @Expose
    private String sbrsCl;      //부대시설

    @SerializedName("wtrplCo")
    @Expose
    private String wtrplCo;     //개수대 개수

    //-------------------------------------------------
    @SerializedName("sbrsEtc")
    @Expose
    private String sbrsEtc;     //부대시설 기타

    @SerializedName("posblFcltyCl")
    @Expose
    private String posblFcltyCl;   //주변이용가능시설

    @SerializedName("posblFcltyEtc")
    @Expose
    private String posblFcltyEtc;  //주변이용가능시설 기타

    @SerializedName("clturEventAt")
    @Expose
    private String clturEventAt;   //자체문화행사 여부(Y:사용, N:미사용)

    @SerializedName("clturEvent")
    @Expose
    private String clturEvent;     //자체문화행사명

    @SerializedName("exprnProgrmAt")
    @Expose
    private String exprnProgrmAt;  //체험프로그램 여부(Y:사용, N:미사용)


    //-------------------------------------------------
    @SerializedName("exprnProgrm")
    @Expose
    private String exprnProgrm;     //체험프로그램명

    @SerializedName("extshrCo")
    @Expose
    private String extshrCo;       //소화기 개수

    @SerializedName("frprvtWrppCo")
    @Expose
    private String frprvtWrppCo;       //방화수 개수

    @SerializedName("frprvtSandCo")
    @Expose
    private String frprvtSandCo;       //방화사 개수

    @SerializedName("fireSensorCo")
    @Expose
    private String fireSensorCo;       //화재감지기 개수

    @SerializedName("themaEnvrnCl")
    @Expose
    private String themaEnvrnCl;       //테마환경


    //-------------------------------------------------
    @SerializedName("eqpmnLendCl")
    @Expose
    private String eqpmnLendCl;        //캠핑장비대여

    @SerializedName("animalCmgCl")
    @Expose
    private String animalCmgCl;        //애완동물출입

    @SerializedName("tourEraCl")
    @Expose
    private String tourEraCl;      //여행시기

    @SerializedName("firstImageUrl")
    @Expose
    private String firstImageUrl;      //대표이미지

    @SerializedName("createdtime")
    @Expose
    private String createdtime;        //등록일

    @SerializedName("modifiedtime")
    @Expose
    private String modifiedtime;       //수정일

    @SerializedName("syncStatus")
    @Expose
    private String syncStatus;  //업데이트 구분



    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public String getAllar() {
        return allar;
    }

    public void setAllar(String allar) {
        this.allar = allar;
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

    public String getFacltDivNm() {
        return facltDivNm;
    }

    public void setFacltDivNm(String facltDivNm) {
        this.facltDivNm = facltDivNm;
    }

    public String getMangeDivNm() {
        return mangeDivNm;
    }

    public void setMangeDivNm(String mangeDivNm) {
        this.mangeDivNm = mangeDivNm;
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

    public String getLctCl() {
        return lctCl;
    }

    public void setLctCl(String lctCl) {
        this.lctCl = lctCl;
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

    public String getMapX() {
        return mapX;
    }

    public void setMapX(String mapX) {
        this.mapX = mapX;
    }

    public String getMapY() {
        return mapY;
    }

    public void setMapY(String mapY) {
        this.mapY = mapY;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    public String getManageNmpr() {
        return manageNmpr;
    }

    public void setManageNmpr(String manageNmpr) {
        this.manageNmpr = manageNmpr;
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

    public String getGlampSiteCo() {
        return glampSiteCo;
    }

    public void setGlampSiteCo(String glampSiteCo) {
        this.glampSiteCo = glampSiteCo;
    }

    public String getCaravSiteCo() {
        return caravSiteCo;
    }

    public void setCaravSiteCo(String caravSiteCo) {
        this.caravSiteCo = caravSiteCo;
    }

    public String getIndvdlCaravSiteCo() {
        return indvdlCaravSiteCo;
    }

    public void setIndvdlCaravSiteCo(String indvdlCaravSiteCo) {
        this.indvdlCaravSiteCo = indvdlCaravSiteCo;
    }

    public String getSitedStnc() {
        return sitedStnc;
    }

    public void setSitedStnc(String sitedStnc) {
        this.sitedStnc = sitedStnc;
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

    public String getPrmisnDe() {
        return prmisnDe;
    }

    public void setPrmisnDe(String prmisnDe) {
        this.prmisnDe = prmisnDe;
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

    public String getBrazierCl() {
        return brazierCl;
    }

    public void setBrazierCl(String brazierCl) {
        this.brazierCl = brazierCl;
    }

    public String getSbrsCl() {
        return sbrsCl;
    }

    public void setSbrsCl(String sbrsCl) {
        this.sbrsCl = sbrsCl;
    }

    public String getWtrplCo() {
        return wtrplCo;
    }

    public void setWtrplCo(String wtrplCo) {
        this.wtrplCo = wtrplCo;
    }

    public String getSbrsEtc() {
        return sbrsEtc;
    }

    public void setSbrsEtc(String sbrsEtc) {
        this.sbrsEtc = sbrsEtc;
    }

    public String getPosblFcltyCl() {
        return posblFcltyCl;
    }

    public void setPosblFcltyCl(String posblFcltyCl) {
        this.posblFcltyCl = posblFcltyCl;
    }

    public String getPosblFcltyEtc() {
        return posblFcltyEtc;
    }

    public void setPosblFcltyEtc(String posblFcltyEtc) {
        this.posblFcltyEtc = posblFcltyEtc;
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

    public String getExprnProgrm() {
        return exprnProgrm;
    }

    public void setExprnProgrm(String exprnProgrm) {
        this.exprnProgrm = exprnProgrm;
    }

    public String getExtshrCo() {
        return extshrCo;
    }

    public void setExtshrCo(String extshrCo) {
        this.extshrCo = extshrCo;
    }

    public String getFrprvtWrppCo() {
        return frprvtWrppCo;
    }

    public void setFrprvtWrppCo(String frprvtWrppCo) {
        this.frprvtWrppCo = frprvtWrppCo;
    }

    public String getFrprvtSandCo() {
        return frprvtSandCo;
    }

    public void setFrprvtSandCo(String frprvtSandCo) {
        this.frprvtSandCo = frprvtSandCo;
    }

    public String getFireSensorCo() {
        return fireSensorCo;
    }

    public void setFireSensorCo(String fireSensorCo) {
        this.fireSensorCo = fireSensorCo;
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

    public String getFirstImageUrl() {
        return firstImageUrl;
    }

    public void setFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

}