## HRSystem

## 1.一整套的逻辑：
```xml
domain中代码为数据库表映射类(类似与.net core 中的Entity)
action中为页面请求直接请求接口，action请求service--dao---domain
attend表:
1	2018-04-03 null		1	1	3 ，iscome为1，表示为上班打卡
系统每天定时会进行打卡，但是不会插入punchtime数据。但是iscome的值为1
如果iscome为0, 则表示为下班打卡
```

整个打卡逻辑
```java
// return find("from Attend as a where a.employee = ?0 and a.dutyDay = ?1", emp, dutyDay);
List<Attend> attendList = attendDao.findByEmpAndDutyDay(emp, dutyDay);
        if(attendList == null || attendList.size() == 0) return NO_PUNCH;

        if(attendList.size() == 1 && attendList.get(0).getIsCome() && attendList.get(0).getPunchTime() == null) return COME_PUNCH;

        if(attendList.size() == 1 && attendList.get(0).getPunchTime() == null) return LEAVE_PUNCH;

        if(attendList.size() == 2){
            if(attendList.get(0).getPunchTime() == null && attendList.get(1).getPunchTime() == null) return  BOTH_PUNCH;
            if(attendList.get(1).getPunchTime() == null) return LEAVE_PUNCH;
        }
```
### 转为文字为：
如果获取到当前某个用户的某天数据记录，如果一条都没有，说明没有自动进行打卡
如果有一条数据，并且iscome为1，及punchtime字段为null则可以上班打卡。

如果只有1条数据，并且punchtime为null，则是下班打卡。（iscome 为0）

如果为2条，第一条的punchtime和第二条的punchtime都是null，那么都可以进行打卡。(应该是早上忘记打卡。。。或请假。。。--两次自动打卡都已打过)

如果有2条，并且第2条的punchtime是null，则可以下班打卡。


### applicationContext.xml 中transmanager事务管理是需要的，及切面相关的东西，因为相关属性注入需要SessionFactory关联。


----------------
## 代码结构
```flow
-src 源代码目录
    - org 根目录
    - common 公共组织代码
      - dao 数据查询hibernate基本方法库
    - hrSystem 系统相关业务代码
      - action struts2相关请求action业务代码
      - Dao 具体的数据库查询方法
      - domain 数据库表对应数据类---仅提供给service内部调用（类似Entity）
      - schedule Quartz调度类-具体方法在applicationContext.xml中配置
      - service spring具体操作类，提供给action调用
      - vo 对外返回的数据类
- web 相关web请求及web.xml和spring配置文件存在目录
```
## 调用顺序
```java
jsp --> action --> service --> dao --> domain---↓
           ↑------重新将值返回--vo<---------------
```

authority中包含拦截器，在Struts.xml中定义为拦截栈，需要的action中加入进去
识别器：validation用法为Action名称-validation.xml进行定义就可以进行识别

**** 容易迷惑的东西 ****
EmpManager和MgrManger为spring中的bean容器，action在调用spring中的bean时，spring会自动将其注入进去。
