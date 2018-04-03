package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.StudentMapper;
import com.example.model.Fakultas;
import com.example.model.Mahasiswa;
import com.example.model.ProgramStudi;
import com.example.model.Universitas;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class StudentServiceDatabase implements StudentService
{
    @Autowired
    private StudentMapper studentMapper;


    @Override
    public Mahasiswa selectMahasiswa (String npm)
    {
        log.info ("select mahasiswa with npm {}", npm);
        return studentMapper.selectMahasiswa (npm);
    }


    @Override
    public List<Mahasiswa> selectAllMahasiswa ()
    {
        
        return studentMapper.selectAllMahasiswa ();
    }
    
    
    @Override
    public List <ProgramStudi> selectAllProgramStudi()
    {
        
        return studentMapper.selectAllProgramStudi();

    }
    
    
    @Override
    public ProgramStudi selectProgramStudi (int id)
    {
   
        return studentMapper.selectProgramStudi(id);
    }
    
    
    @Override
    public List <Fakultas> selectAllFakultas ()
    {
        
        return studentMapper.selectAllFakultas ();

    }
    
    @Override
    public Fakultas selectFakultas (int id)
    {
        
        return studentMapper.selectFakultas (id);
    }

   
    @Override
    public List <Universitas> selectAllUniv()
    {
        
        return studentMapper.selectAllUniv();

    }
    
    @Override
    public Universitas selectUniv (int id)
    {
        
        return studentMapper.selectUniv (id);
    }

    
    
	@Override
	public void addMahasiswa(Mahasiswa mahasiswa) {
		// TODO Auto-generated method stub
		studentMapper.addMahasiswa(mahasiswa);
		
	}


	@Override
	public void deleteMahasiswa(String npm) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateMahasiswa(Mahasiswa mahasiswa) {
		// TODO Auto-generated method stub
		studentMapper.updateMahasiswa(mahasiswa);
	}
	
	@Override
	public String selectMahasiswaNpm(String npm) {
		return studentMapper.selectMahasiswaNpm (npm);
		
	}
	
	@Override
	public int selectKelulusan(String tahun_masuk, int prodi) {
		// TODO Auto-generated method stub
		return studentMapper.selectKelulusan(tahun_masuk, prodi);
	}

	@Override
	public int selectTahunKelulusan(String tahun_masuk, int prodi) {
		// TODO Auto-generated method stub
		return studentMapper.selectTahunKelulusan(tahun_masuk, prodi);
	}
	
	 @Override
	 public List <Fakultas> selectAllFakultasbyUniv(int id_univ){

	   return studentMapper.selectAllFakultasbyUniv(id_univ);

	  }
	 
	 
	 @Override
	 public List <ProgramStudi> selectProdibyFak(int id_fakultas){

	   return studentMapper.selectProdibyFak(id_fakultas);

	  }
	 
	 @Override
	 public List <Mahasiswa> selectMahasiswabyProdi(int id_prodi){

	   return studentMapper.selectMahasiswabyProdi(id_prodi);

	  }
	 

	@Override
	public int selectUniversitas(int id) {
		// TODO Auto-generated method stub
		return studentMapper.selectUniversitas(id);
	}
	
	@Override
	public int selectFak(int id) {
		// TODO Auto-generated method stub
		return studentMapper.selectFak(id);
	}

	
	
	

	
 /*   @Override
    public void addMahasiswa (Mahasiswa mahasiswa)
    {
        studentMapper.addMahasiswa (mahasiswa);
    } */


  /*  @Override
    public void deleteMahasiswa (String npm)
    {
        log.info("mahasiswa " + npm + " deleted");
        studentMapper.deleteMahasiswa(npm);
    } */

  /*  @Override
    public void updateMahasiswa(Mahasiswa mahasiswa)
    {
        log.info("mahasiswa {} name update to", mahasiswa.getNpm(),mahasiswa.getName());
        studentMapper.updateMahasiswa(mahasiswa);
    } */

}
