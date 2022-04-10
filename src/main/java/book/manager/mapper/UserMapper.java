package book.manager.mapper;


import book.manager.entity.AuthUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from users where username = #{username}")
    AuthUser getPasswordByUsername(String s);

    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    @Insert("Insert into users(username,role,password) values(#{username}, #{role}, #{password})")
    int registerUser(AuthUser user);

    @Insert("insert into student(uid,name,grade,sex) values(#{uid},#{name},#{grade},#{sex})")
    int addStudentInfo(@Param("uid") int uid,@Param("name") String name,@Param("grade") String grade,@Param("sex") String sex);

    @Select("select sid from student where uid = #{uid}")
    int getSidByUid(int uid);
}
