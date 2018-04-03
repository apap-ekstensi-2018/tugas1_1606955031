package com.example.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Fakultas;
import com.example.model.Mahasiswa;
import com.example.model.ProgramStudi;

import com.example.model.Universitas;
import com.example.service.StudentService;



@Controller
public class StudentController {
	@Autowired
	StudentService mahasiswaDAO;
	
	
	@RequestMapping("/")
	public String index ()
    {
        return "index";
    }
	
	 @RequestMapping("/mahasiswa{npm}")
	    public String view (Model model,
	            @RequestParam(value = "npm", required = false) String npm)
	    {
	        Mahasiswa mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
	        ProgramStudi prodi = mahasiswaDAO.selectProgramStudi(mahasiswa.getId_prodi());
	        Fakultas fakultas = mahasiswaDAO.selectFakultas(prodi.getId_fakultas());
	        Universitas universitas = mahasiswaDAO.selectUniv(fakultas.getId_univ());
	        
	        

	        if (mahasiswa != null) {
	            model.addAttribute ("mahasiswa", mahasiswa);
	            model.addAttribute ("prodi", prodi);
	            model.addAttribute ("fakultas", fakultas);
	            model.addAttribute ("universitas", universitas);
	            return "view";
	        } else {
	        		model.addAttribute ("npm", npm);
	            return "not-found";
	        }
	    }
	
	@RequestMapping("/mahasiswa/tambah")
	public String tambah (@ModelAttribute("mahasiswa") Mahasiswa mahasiswa, Model model)
	{
		if(mahasiswa.getNama()==null) {
			return "addMahasiswa";
		} else {
			mahasiswa.setNpm(generateNpm(mahasiswa));
			mahasiswa.setStatus("Aktif");
			model.addAttribute("npm", mahasiswa.getNpm());
			model.addAttribute("message", "Mahasiswa dengan NPM " + mahasiswa.getNpm() + " berhasil ditambahkan");
			mahasiswaDAO.addMahasiswa(mahasiswa);
			return "success-add";
		}
	}
	
	public String generateNpm(Mahasiswa mahasiswa) {
		ProgramStudi prodi = mahasiswaDAO.selectProgramStudi(mahasiswa.getId_prodi());
		Fakultas fakultas = mahasiswaDAO.selectFakultas(prodi.getId_fakultas());
		Universitas universitas = mahasiswaDAO.selectUniv(fakultas.getId_univ());
		
		String duadigit  = mahasiswa.getTahun_masuk().substring(2, 4);
		String upDigit = universitas.getKode_univ()+prodi.getKode_prodi();
		String tigadigit="";
		    	if (mahasiswa.getJalur_masuk().equals("Olimpiade Undangan")) {
		    		tigadigit = "53";
		    	}
		    	else if(mahasiswa.getJalur_masuk().equals("Undangan Reguler/SNMPTN")) {
		    		tigadigit="54";
		    	}
		    	else if (mahasiswa.getJalur_masuk().equals("Undangan Paralel/PPKB")) {
		    		tigadigit="55";
		    	}
		    	else if (mahasiswa.getJalur_masuk().equals("Ujian Tulis Mandiri")) {
		    		tigadigit="62";
		    	}
		    	String npmFiks = duadigit + upDigit + tigadigit;
		    	String cekNpm = mahasiswaDAO.selectMahasiswaNpm(npmFiks);
		    	if(cekNpm != null) {
		    	cekNpm = "" + (Integer.parseInt(cekNpm) + 1);
		    	if (cekNpm.length() == 1) {
    			npmFiks = npmFiks + "00" + cekNpm;
    		}else if (cekNpm.length() == 2) {
    			npmFiks = npmFiks + "0" + cekNpm;
    		}else {
    			npmFiks = npmFiks + cekNpm;
    		}
    	}else {
    		npmFiks = npmFiks + "001";
    	}
		    	return npmFiks;
	}
	
	@RequestMapping("/mahasiswa/update/{npm}")
    public String ubah (@PathVariable(value = "npm") String npm, Model model, 
    		@ModelAttribute("mahasiswa") Mahasiswa newmahasiswa)
    {
    		Mahasiswa mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
    		
    		if(newmahasiswa.getNama()==null) {
    			if(mahasiswa==null) {
    				model.addAttribute("npm", npm);
    				return "not-found";
    			}
    			model.addAttribute("mahasiswa", mahasiswa);
    			model.addAttribute("title", "Update Mahasiswa");
    			return "editMahasiswa";
    		} else {
    			if(!mahasiswa.getTahun_masuk().equals(newmahasiswa.getTahun_masuk()) || 
    					mahasiswa.getId_prodi() != newmahasiswa.getId_prodi() || 
    					!mahasiswa.getJalur_masuk().equals(newmahasiswa.getJalur_masuk())) {
    				newmahasiswa.setNpm(generateNpm(newmahasiswa));

        			mahasiswaDAO.updateMahasiswa(newmahasiswa);
        			model.addAttribute("npm", mahasiswa.getNpm());
        			model.addAttribute("title", "Update Mahasiswa");
        			return "success-update";
    			}else {
    				newmahasiswa.setNpm(mahasiswa.getNpm());

        			mahasiswaDAO.updateMahasiswa(newmahasiswa);
        			model.addAttribute("npm", mahasiswa.getNpm());
        			model.addAttribute("title", "Update Mahasiswa");
        			return "success-update";
    			}
    		}
    }
	
	@RequestMapping("/kelulusan")
    public String kelulusan(Model model,
    						@RequestParam(value = "tahun_masuk", required = false) Optional<String> tahun_masuk,
    						@RequestParam(value = "prodi", required = false) Optional<String> prodi)
{
	if (tahun_masuk.isPresent() && prodi.isPresent()) {
		int jml_mahasiswa = mahasiswaDAO.selectTahunKelulusan(tahun_masuk.get(), Integer.parseInt(prodi.get()));
		int jml_mahasiswaLulus = mahasiswaDAO.selectKelulusan(tahun_masuk.get(), Integer.parseInt(prodi.get()));
		double peresent = ((double) jml_mahasiswaLulus / (double) jml_mahasiswa) * 100;
		String presentase = new DecimalFormat("##.##").format(peresent);
		
		ProgramStudi program_studi = mahasiswaDAO.selectProgramStudi(Integer.parseInt(prodi.get()));
		Fakultas fakultas = mahasiswaDAO.selectFakultas(program_studi.getId_fakultas());
		Universitas universitas = mahasiswaDAO.selectUniv(fakultas.getId_univ());
		model.addAttribute("jml_mahasiswa", jml_mahasiswa);
		model.addAttribute("jml_mahasiswaLulus", jml_mahasiswaLulus);
		model.addAttribute("presentase", presentase);
		model.addAttribute("tahun_masuk", tahun_masuk.get());
		model.addAttribute("prodi", program_studi.getNama_prodi());
		model.addAttribute("fakultas", fakultas.getNama_fakultas());
		model.addAttribute("universitas", universitas.getNama_univ());
		
		return "viewKelulusan";
		
	}else {
		List<ProgramStudi> programStudi = mahasiswaDAO.selectAllProgramStudi();
		model.addAttribute("programStudi", programStudi);
		return "kelulusan";
	}
}   
	
	@RequestMapping("/mahasiswa/cari")
	public String cariMahasiswa(Model model,
			@RequestParam(value = "universitas", required = false) String univ,
			@RequestParam(value = "idfakultas", required = false) String idfakultas,
			@RequestParam(value = "idprodi", required = false) String idprodi)
	{

			List<Universitas> universitas = mahasiswaDAO.selectAllUniv();
			model.addAttribute ("universitas", universitas);
            	if(univ== null) {
            		return "cari-universitas";
            	} else {
            		int idUniv = Integer.parseInt(univ);
            		Universitas univers = mahasiswaDAO.selectUniv(idUniv);
            		int idUnivv = mahasiswaDAO.selectUniversitas(idUniv);
            		List<Fakultas> fakultas = mahasiswaDAO.selectAllFakultasbyUniv(idUnivv);
            		if (idfakultas == null) {
            			model.addAttribute("fakultas", fakultas);
            			model.addAttribute("selectUniv",idUniv);
                		return "cari-fakultas";
            		}  
            		else {
            			int idFakul = Integer.parseInt(idfakultas);
            			Fakultas fakultass = mahasiswaDAO.selectFakultas(idFakul);
            			int idFaks = mahasiswaDAO.selectFak(idFakul);
            			model.addAttribute("selectFak", idFakul);
            			List<ProgramStudi> prodd = mahasiswaDAO.selectProdibyFak(idFaks);
            			
            			if(idprodi == null) {
            				model.addAttribute("fakultas", fakultas);
                			model.addAttribute("selectUniv",idUniv);
                			model.addAttribute("prodi",prodd);
                			
                    		return "cari-prodi";
            			}else {
            				int idprod = Integer.parseInt(idprodi);
            				ProgramStudi prodis = mahasiswaDAO.selectProgramStudi(idprod);
            				List<Mahasiswa> mahasiswaByProdi = mahasiswaDAO.selectMahasiswabyProdi(idprod);
            				model.addAttribute("mahasiswaByProdi", mahasiswaByProdi);
            				ProgramStudi prodiSelectObject = mahasiswaDAO.selectProgramStudi(idprod);
            				model.addAttribute("prodiSelectObject", prodiSelectObject);
            				
            				return "tabelMahasiswa";
            				
            			}
            			
            		}
            		
            	}
	
	
	}
}
    
		
	