package com.example.service;

import java.util.List;

import com.example.model.Fakultas;
import com.example.model.Mahasiswa;
import com.example.model.ProgramStudi;
import com.example.model.Universitas;

public interface StudentService
{
    Mahasiswa selectMahasiswa (String npm);
    ProgramStudi selectProgramStudi(int id);
    Fakultas selectFakultas(int id);
    Universitas selectUniv(int id);

    String selectMahasiswaNpm(String npm);
    List<Mahasiswa> selectAllMahasiswa ();
    List<Fakultas> selectAllFakultas ();
    List<ProgramStudi> selectAllProgramStudi ();
    List<Universitas> selectAllUniv ();
    
    int selectKelulusan (String tahun_masuk, int prodi);
    int selectTahunKelulusan (String tahun_masuk, int prodi);
    int selectUniversitas (int id);
    int selectFak (int id);
    
    List <Fakultas> selectAllFakultasbyUniv(int id_univ);
    List <ProgramStudi> selectProdibyFak(int id_fakultas);
    List <Mahasiswa> selectMahasiswabyProdi(int id_prodi);
  
    void addMahasiswa (Mahasiswa mahasiswa);
    void updateMahasiswa (Mahasiswa mahasiswa);

    void deleteMahasiswa (String npm);

    


	
}
