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


# applicationContext.xml 中transmanager事务管理是需要的，及切面相关的东西，因为相关属性注入需要SessionFactory关联。
