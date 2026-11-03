<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- author:#{author} #{date} -->
<!--  #{tableComment} 查询脚本 -->
<mapper namespace="com.work.ppm.application.model.#{model}.#{classNameUpper}">
	 <resultMap type="#{classNameLower}" id="#{classNameUpperAll}_ALL">
        <% for { %>
        <result property="@filedLower@" column="@filedUpper@" />
        <% } %>
     </resultMap>

     <!-- 所有字段名称 -->
    <sql id="sql_flied_all">
        @filedListUpper@
    </sql>
    <sql id="sql_select_condition">
        <trim prefix="WHERE" suffixOverrides="AND">
            <% for { %>
            <if test="@filedLower@ != null @Null@" > @filedUpper@ = #{@filedLower@} AND </if>
            <% } %>
        </trim>
    </sql>
    <!-- 查询分页  #{date} #{author}  -->
    <select id="queryPagerList" parameterType="map" resultMap="#{classNameUpperAll}_ALL">
        SELECT
        <include refid="sql_flied_all" />
        FROM #{tableName}
        <include refid="sql_select_condition" />
    </select>

    <!-- 新增  #{date} #{author}  -->
    <insert id="addPojo" parameterType="#{classNameLower}">
        INSERT INTO #{tableName} (
        <trim suffixOverrides=",">
         <% for { %>
        <if test="@filedLower@ != null @Null@" > @filedUpper@ , </if>
          <% } %>
          </trim>
        )
        VALUES(
        <trim suffixOverrides=",">
         <% for { %>
        <if test="@filedLower@ != null @Null@" > #{@filedLower@} , </if>
        <% } %>
        </trim>
        )
    </insert>

    <!--通过id查询  #{author} #{date} -->
    <select id="queryDetail" parameterType="#{classNameLower}" resultMap="#{classNameUpperAll}_ALL">
        SELECT
        <include refid="sql_flied_all" />
        FROM #{tableName}  WHERE @filedIdUpper@=#{@filedIdLower@}
    </select>

     <!--查询集合  #{author} #{date} -->
    <select id="getList" parameterType="#{classNameLower}" resultMap="#{classNameUpperAll}_ALL">
        SELECT
        <include refid="sql_flied_all" />
        FROM #{tableName}
        <include refid="sql_select_condition" />
    </select>
    <!-- update sql -->
    <sql id="update_pojo_sql">
        <trim prefix="SET" suffixOverrides=",">
            <% for { %>
            <if test="@filedLower@ != null @Null@" > @filedUpper@=#{@filedLower@} , </if>
            <% } %>
        </trim>
    </sql>

    <!--通过id编辑 #{author} #{date} -->
    <update id="updatePojo" parameterType="#{classNameLower}">
        UPDATE
        #{tableName}
        <include refid="update_pojo_sql" />
        WHERE @filedIdUpper@=#{@filedIdLower@}
    </update>

    <!--通过id批量删除  #{author} #{date} -->
    <delete id="deleteById" parameterType="#{classNameLower}">
        DELETE
        FROM
        #{tableName}
        WHERE @filedIdUpper@ = #{@filedIdLower@}
    </delete>

</mapper>