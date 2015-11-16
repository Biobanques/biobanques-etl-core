package fr.inserm.bean;

import java.util.Date;
import java.util.List;

import fr.inserm.bean.v2.EchantillonBean;

public class FileInputBean {

	private String nameFile;

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	private Date dateImport;

	private int formatVersion;

	private SiteBean site;

	private List<EchantillonBean> echantillons;

	public Date getDateImport() {
		return dateImport;
	}

	public void setDateImport(Date dateImport) {
		this.dateImport = dateImport;
	}

	public int getFormatVersion() {
		return formatVersion;
	}

	public void setFormatVersion(int formatVersion) {
		this.formatVersion = formatVersion;
	}

	public SiteBean getSite() {
		return site;
	}

	public void setSite(SiteBean site) {
		this.site = site;
	}

	public List<EchantillonBean> getEchantillons() {
		return echantillons;
	}

	public void setEchantillons(List<EchantillonBean> echantillons) {
		this.echantillons = echantillons;
	}

}
