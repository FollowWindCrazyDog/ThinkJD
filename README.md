![ThinkJDBC](https://gitee.com/uploads/images/2018/0428/174620_372c5f0f_890966.png)

[**�����ĵ� V1.4.2_10**](https://blog.csdn.net/Leytton/article/details/80021702)

[**English Document V1.3.1_8**](https://blog.csdn.net/Leytton/article/details/80021029)

���°汾 V1.4.2_10

# 1 ���

`ThinkJD`������`ThinkJDBC`��һ������ǿ��Ŀ�ԴJDBC�����⡣�����ʹ��Java��`ThinkPHP`��ܵ�M����һ����`һ�д���㶨���ݿ����`��ThinkJD���Զ��������ݿ����ӣ�ʹ����ϻ�����쳣����ر�������������ڴ������

**�ȶ�Ϊ�죺**
```
//���ݿ�����(ֻ�����һ��)
D.setDbConfig("jdbc:mysql://127.0.0.1:3306/DbName?characterEncoding=UTF-8","root","root");

//JavaBeanģʽ���Զ���ȡ�������������������ԡ��ֶ���������
User user = new User();
user.setAge(10);
user.setName("Hello");
user.setSex(true);
//��������
long id=D.M(user).add();
//��ѯ����
user=D.M(User.class).find(id);
//��������
user.setSex(false);
D.M(user).field("sex").save();//��ָ���ֶ���Ĭ�ϸ���JavaBean�����зǿ�����
//ɾ������
D.M(user).delete();
//D.M(User.class).delete(id);

//Tableģʽ���ֶ�ָ���������������������ԡ��ֶ���������
//��������
long id=D.M("user").field("name,weight").data("Tom",60).add();
//��������
D.M("user").field("name,weight").data("Tom",100).where("id=?",id).save();
//��ѯ����
user=D.M(User.class).find(id);
//ɾ������
D.M("user").delete(id);
```

**��Ŀ��ҳ** https://github.com/Leytton/ThinkJD (Github)   https://gitee.com/Leytton/ThinkJD (����)

**������Ŀ** https://github.com/Leytton/ThinkJD_Demo

**������ҳ** https://blog.csdn.net/Leytton

# 2 ʹ�÷���

## 0x01 �������
[ThinkJDBC-x.x.x-full.jar](https://github.com/Leytton/ThinkJD/releases) ������ThinkJDBC-x.x.x-core.jar���Ŀ�����������⣬ֻ��Ҫ�����һ��jar��������

 - mysql-connector-java-5.1.39.jar
 - commons-dbutils-1.6.jar

 
## 0x02 �������ݿ�
ThinkJD֧��ֱ�Ӷ����û�������������ݿ⣬Ҳ֧��ʹ��Hikari��C3P0�����ݿ����ӳء�

**���ݿ����ӷ�ʽ�����֣�**

### (1)�����ļ���ʽ
����Ŀ��Ŀ¼������ļ�(��Hikari�����ļ���ʽһ��) 

�����һ������ʱ���Զ����ض�ȡ�����ļ�������ļ�����������ԡ���V1.2.4_5 ���ӹ��ܡ�

`thinkjdbc.properties`
```
jdbcUrl = jdbc:mysql://127.0.0.1:3306/thinkjdbc?useUnicode=true&characterEncoding=UTF-8
dataSource.user = root
dataSource.password = root
```

### (2)�ʺ����뷽ʽ
```
D.setDbConfig("jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&characterEncoding=UTF-8","root","root");
```

### (3)ʹ�����ݿ����ӳ�
����ʹ��Hikari���ӳأ� 
```
HikariConfig config = new HikariConfig("/hikari.properties");
HikariDataSource dataSource = new HikariDataSource(config);
D.setDataSource(dataSource);
```
`ע��������������ݿ����ӳأ�ThinkJD������ʹ�á�`


### (3)���ñ�ǰ׺
ֻ�����һ�Σ����ñ�ǰ׺���Ǳ����
```
D.setTablePrefix("jd_");
//D.M('user') D.M(User.class)������� `jd_user` ��
```

> ע:`D.M('user').prefix('jd_')`�����ɵ���ָ����ǰ׺��V1.2.3������

## 0x03 �������
| ����| ����| ʾ�� |˵��| 
| ------------- |------------- |------------- | -------------
|`table`|table(String table) | table("user") | 
|`pk`|pk(String key) | pk("id") | 
|`autoInc`|autoInc(boolean isPkAutoInc) | autoInc(false) | 
|`join` |join(String join)| join("left join machine on user.id=user_id and machine_status=1")|
|`field`|field(String filed)| field("id,username")| 
|`data`|data(Object... dataParam)| data(11,"Leytton")| 
|`setInc`|setInc(String key,long num)| setInc("gold",5)| 
|`setDec`|setDec(String key,long num)| setDec("gold",5)| 
|`where`|��where(String where)<br>��where(String where, Object... whereParam)|��where("id=1111 and username='Leytton'")<br> ��where("id=? and username=?",1111,"Leytton")
|`group`|group(String group)|group("type")
|`having`|having(String having)|having("id>1234")
|`order`|order(String order)|order("id desc")
|`asc`|asc(String key)|asc("id")
|`desc`|desc(String key)|desc("id")
|`page`|page(long page, long rows)|page(1,10)
|`limit`|��limit(long rows)<br>��limit(long offset, long rows)|��limit(10)<br>��limit(1,10)
|`union`|union(String union,Boolean isAll)|��union("select * from user_two where id>1234",false)<br>��union("select * from user_two where id>1234",true)

## 0x04 ��ѯ����

| ����| ����| ˵��|
| -------- |--------|--------
|select|<`T`> List<`T`> select()
|find|��<`T`> T find()<br>��<`T`> T find(Object value)<br>��<`T`> T find(String key, Object value)
|count|��long count()<br>��long count(String field)
|max|double max(String field)
|min|double min(String field)
|avg|double avg(String field)
|sum|double sum(String field)

```
//find��ѯ
//select id,name from jd_user where id>4 order by id asc limit 0,1
User res = D.M(User.class).field("id,name").where("id>?",4).order("id asc").find();

//find ����id��ѯ
//select * from jd_user where id=3 limit 0,1
User user = D.M(User.class).find(3);

//find�����ֶβ�ѯ
//select * from jd_user where name='Tom' limit 0,1
User user=D.M(User.class).fetchSql(true).find("name","Bob");

//where,field����
//select id,name,weight from jd_user where id>3
List<User> res = D.M(User.class).field("id,name,weight").where("id>3").select();

//group�����ѯ
//select sex,sum(weight) as weight,avg(age) as age,count(id) as num from jd_user where id>5 group by sex order by sex desc limit 0,10
res = D.M(User.class).field("sex,sum(weight) as weight,avg(age) as age,count(id) as num").where("id>?",5).group("sex").order("sex desc").page(1, 10).select();

//join�����ѯ
//select jd_user.id,name,weight,sum(gold) as num from jd_user left join jd_gold on user_id=jd_user.id where jd_user.id>3 group by jd_user.id
res = D.M(User.class).field("jd_user.id,name,weight,sum(gold) as num").join("left join jd_gold on user_id=jd_user.id").where("jd_user.id>3").group("jd_user.id").select();

//union�����ѯ
//(select id,name from jd_user where id=4 ) union all (select id,name from jd_user where id<3) union (select id,name from jd_user where id=3)
res = D.M(User.class).field("id,name").where("id=4").union("select id,name from jd_user where id<3",true)
	.union("select id,name from jd_user where id=3",false).select();

//ͳ�Ʋ�ѯ
long num= new M(User.class).where("id>3").count();
num= D.M(User.class).fetchSql(true).where("id>3").count("id");
num= (long) D.M(User.class).fetchSql(false).where("id<0").max("id");
num= (long) D.M(User.class).where("id<3").max("id");
num= (long) D.M(User.class).min("id");
num= (long) D.M(User.class).where("id>3").min("id");
num= (long) D.M(User.class).fetchSql(false).where("id>3").avg("id");
double avg= D.M(User.class).fetchSql(false).where("id>3").avg("id");
num= (long) D.M(User.class).where("id>13441").sum("age");
```

> ͨ������`fetchSql(true)`���������Ի�ȡ�� `ThinkJD`������SQL���(Exception��ʽ)���Ҳ���ִ�����ݿ������
![fetchSql](https://img-blog.csdn.net/2018042023324417)

user��ṹ��

|�ֶ��� | ��������| ��ע|
|--------|--------|--------
|id |int | �û�id,����������
|name | varchar | �û���
|age |tinyint|����
|weight | float | ����
|sex|tinyint|�Ա� 0Ů/1��
|time|int|ʱ��

`select()`�� `find()`��ѯ�����װ��JavaBean�ﷵ�أ�JavaBean��ʹ��ע��ӳ�����ݿ��ֶΡ�

> `ע��:ǽ�ѽ���JavaBean�ֶλ�����������ʹ�á�Integer��Long��Boolean��Float��Double��Byte��Short��Char����Ҫʹ�á�integer��long��boolean��float��double��byte��short��char������Ϊǰ�߿��Ը�ֵΪnull�����߲���(nullʱΪ0)�����Ի�ȡ����ֵ�ǲ�׼ȷ�ġ�ThinkJD��save���µȲ���ͨ���ж�����ֵ��Ϊnull��������ݿ�����ֶζ��С�ThinkJD���Զ�������ϲ����ϵ��������Ͳ��������档����رյ���D.setCheckField(false);`

```
//@Table(name="user")Ĭ������Ϊ����,��ע���ض���
public class User {
	//@Column(isKey=true)Ĭ��idΪ������isAutoInc=true����,��ע���ض���
	private Long id;
	private Integer age;
	//@Column(name="user_name")Ĭ��������Ϊ���ֶ�,��ע���ض���
	private String name;
	private Float weight;
	private Boolean sex;
	
	@Column(isColumn=false)
	private Integer num;
	
	private Long time;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}

```

## 0x05 ��������
| ����| ����| ˵��|
| -------- | -------- | --------
|add|long add()|Tableģʽǰ�᷽��:data()<br>�����Զ����ɵ�����ֵ;

```
/*ָ�������ֶ�*/
long id=D.M(User.class).field("name,weight").data("Tom",60).add();

/*��ָ�������ֶΣ������ֶ�˳�����*/
id=D.M("user").data(null,"Tom",60,...).add();

/*ʹ��javaBean���Զ�ģʽ���Զ���ȡ�������������ֶ���������data��javaBean����˳�����,���ɵ�sql�������
 *insert into jd_user (age,name,weight,sex,time) values(?,?,?,...)
 */
id=D.M(User.class).data("Tom",60,...).add();

//ʹ��javaBeanȫ�Զ�ģʽ���Զ���ȡ�������������ֶ���������
User user = new User();
user.setId(5);
user.setAge(10);
user.setName("Hello");

//insert into jd_user (age,name) values(?,?) Params[10,Hello]
num=D.M(user).add();

//insert into jd_user (name) values(?) Params[Hello]
num=D.M(user).field("name").add();

//insert into jd_user (id,age,name) values(?,?,?) Params[5,10,Hello]
num=D.M(user).autoInc(false).add();
```

## 0x06 ��������
| ����| ����|˵��|
| -------- | -------- | -------- 
|save|long save()|Tableģʽǰ�᷽��:data(),where();<br>����ִ����Ч����

```
long num=D.M("user").field("name,weight").data("Mike",100).where("id=?",1234).save();
User user = new User();
user.setId(5);
user.setAge(10);
user.setName("Hello");

//update jd_user set age=?,name=? where id=?; Params[10,Hello,5]
num=D.M(user).save();

//update jd_user set name=? where id=?; Params[Hello,5]
num=D.M(user).field("name").save();

//update jd_user set id=?,age=?,name=? where id=?; Params[5,10,Hello,4]
id=D.M(user).autoInc(false).fetchSql(true).where("id=?",user.getId()-1).save();
```

## 0x07 ɾ������
| ����| ����|˵��|
| -------- | -------- | -------- 
|delete|long delete()|Tableģʽǰ�᷽��:where()<br>����ִ����Ч����

`ע��Ϊ��ֹ��ɾ����where��������Ϊ�ա�`

```
long num=D.M("user").delete(5);//Ĭ��Ϊid=?
num=D.M("user").delete("time",1523681398);//time=?
num=D.M(User.class).where("id>=?",13421).delete();

//JavaBeanģʽ
User user=new User();
user.setId(10L);
long num=D.M(user).delete();
```

## 0x08 ִ��SQL

| ����| ����|˵��| 
| -------- | -------- | -------- 
|execute|void execute(String... sqls)|ֱ��ִ��SQL���

```
D.M().execute( sql1 [ sql2 , sql3 ... ] );
```

## 0x09 ����֧��
���ݿ������Ӧ��ΪInnoDB��֧�����������
`����ʾ����`

```
Connection conn=null;
try {
	//��ȡ�ѿ�����������ݿ�����
	conn = D.M().startTrans();
	//ʹ���������Ӳ������ݿ�
	long id=new M("gold").trans(conn).field("user_id,gold,type,time").data(3,5,0,System.currentTimeMillis()/1000).add();
	System.out.println(id);
	if(id>0) {
		throw new SQLException("Transaction Rollback Test");
	}
	id=new M("gold").trans(conn).field("user_id,gold,type,time").data(3,5,0,System.currentTimeMillis()/1000).add();
	System.out.println(id);
	//�ύ����
	D.M().commit(conn);
} catch (SQLException e) {
	e.printStackTrace();
	try {
		//����ع�
		D.M().rollback(conn);
	} catch (SQLException e1) {
		e1.printStackTrace();
	}
}
```

# 3 ���֤

[Apache License 2.0](https://github.com/Leytton/ThinkJD/blob/master/LICENSE) 

# 4 ����
���ϲ���Ļ�������������֪��Ŷ~���ҵ������õø�˳�ֵ�JDBC��֮ǰ�������Ŀ��������¡�