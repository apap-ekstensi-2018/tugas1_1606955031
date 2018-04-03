package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

import com.example.model.Fakultas;
import com.example.model.Mahasiswa;
import com.example.model.ProgramStudi;
import com.example.model.Universitas;

@Mapper
public interface StudentMapper
{
	
	@Select("SELECT * FROM mahasiswa m")
    List<Mahasiswa> selectAllMahasiswa();
	
	@Select("SELECT * from mahasiswa WHERE npm = #{npm}")
	Mahasiswa selectMahasiswa(@Param("npm")String npm);
	
	@Select("SELECT p.* FROM program_studi p")
    List<ProgramStudi> selectAllProgramStudi();
	
	@Select("SELECT * FROM program_studi WHERE id = #{id}")
    ProgramStudi selectProgramStudi (@Param("id") int id);
   
	@Select("SELECT * FROM fakultas")
    List<Fakultas> selectAllFakultas();
    
    @Select("SELECT * from fakultas where id = #{id}")
    Fakultas selectFakultas (@Param("id")int id);
    
    @Select("SELECT * FROM universitas")
    List<Universitas> selectAllUniv();
    
    @Select("SELECT * FROM universitas WHERE id = #{id}")
    Universitas selectUniv (@Param("id") int id);
    
    @Select("SELECT substring(npm, 10,3) FROM mahasiswa WHERE npm LIKE '${npm}%' ORDER BY substring(npm, 10, 3) DESC LIMIT 1")
    String selectMahasiswaNpm (@Param("npm")String npm);
 
   @Insert("INSERT INTO mahasiswa (npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, tahun_masuk, jalur_masuk,status,id_prodi)"
   		+ "VALUES (#{npm}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin},"
   		+ " #{agama}, #{golongan_darah}, #{tahun_masuk}, #{jalur_masuk}, #{status}, #{id_prodi})")
    void addMahasiswa (Mahasiswa mahasiswa);
   
   @Update("UPDATE mahasiswa SET npm = #{npm}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir=#{tanggal_lahir}, jenis_kelamin=#{jenis_kelamin},"
   		+ "agama=#{agama},golongan_darah=#{golongan_darah}, tahun_masuk=#{tahun_masuk}, jalur_masuk=#{jalur_masuk},"
   		+ "id_prodi=#{id_prodi} WHERE id = #{id}")
   void updateMahasiswa (Mahasiswa mahasiswa);
   
   @Select("select count(*) from mahasiswa where tahun_masuk =#{tahun_masuk} and id_prodi= #{prodi} and status = 'Lulus'")
   Integer selectKelulusan (@Param ("tahun_masuk") String tahun_masuk, @Param("prodi")int prodi);
   
   @Select("select count(*) from mahasiswa where tahun_masuk=#{tahun_masuk} and id_prodi= #{prodi}")
   Integer selectTahunKelulusan (@Param ("tahun_masuk") String tahun_masuk, @Param("prodi")int prodi);
   
   @Select("SELECT * from fakultas where id_univ=#{id_univ}")
   List<Fakultas> selectAllFakultasbyUniv(@Param ("id_univ")int id_univ);
   
   @Select("SELECT * from program_studi where id_fakultas=#{id_fakultas}")
   List<ProgramStudi> selectProdibyFak(@Param ("id_fakultas")int id_fakultas);
   
   @Select("SELECT * from mahasiswa where id_prodi=#{id_prodi}")
   List<Mahasiswa> selectMahasiswabyProdi(@Param ("id_prodi")int id_prodi);
   
   @Select("SELECT * from universitas where id =#{id}")
   int selectUniversitas (@Param("id") int id);
   
   @Select("SELECT * from fakultas where id =#{id}")
   int selectFak (@Param("id") int id);
   
   
   
   

   /* @Delete("delete from mahasiswa where npm = #{npm} ")
    void deleteMahasiswa (String npm); */

    
}
