package com.tansun.wf.activiti.util.dbtool.tool;

import com.tansun.wf.activiti.util.dbtool.cons.ValCons;
import com.tansun.wf.activiti.util.dbtool.entity.*;
import com.tansun.wf.activiti.util.dbtool.exception.DbToolException;

import java.io.File;
import java.util.*;

/**
 * 简易文本数据库执行类
 * @author 罗道宏
 */
public class Operater {

	private String comm;

	public Operater(String s){
		this.comm = Tools.delSpaceEnter(s).trim();
		if(this.comm.endsWith(";")) {
            this.comm = this.comm.substring(0, this.comm.length() - 1);
        }
		checkMatch();
	}

	private void checkMatch(){
		int len = comm.length();
		int top=-1;
		for(int i=0;i<len;i++) {
			if(comm.charAt(i)=='(') {
				++top;
			} else if(comm.charAt(i)==')') {
				if(top>=0) {
					top--;
				} else {
					throw new DbToolException("括号不匹配！！");
				}
			}
		}
	}

	public Table checkCreate(){
		String[] words = comm.split("[\\s]");
		Table table  = new Table();
		if(!words[1].toUpperCase().equals("TABLE")){
			throw new DbToolException("无法解析 创建表语句！");
		}
		StringBuilder na = new StringBuilder();
		for(int i=13;i<comm.length();i++) {
			if(comm.charAt(i)=='(') break;
			na.append(comm.charAt(i));
		}
		if(!na.toString().trim().matches("[a-zA-Z_]+")) {
			throw new DbToolException(na+" 表名创建无效，只能为大小写字母下划线！");
		}
		//设置表名
		table.setName(na.toString().trim());
		String st="";
		for(int i=0;i<comm.length();i++) {
			if(comm.charAt(i)=='(') {
				st = comm.substring(i+1,comm.length()-1);
				break;
			}
		}
		if(st.equals("")){
			throw new DbToolException("括号不匹配！");
		}
		String[] w = st.split(",");
		for (String aW : w) {
			String name = Tools.delSpaceEnter(aW).trim();
			String[] c = name.split("\\s");
			if (c.length != 2) {
				throw new DbToolException(aW + ":属性解析错误！");
			} else {
				if (!c[0].trim().matches("[a-zA-Z0-9_]+") ||
						(c[0].charAt(0) >= '0' && c[0].charAt(0) <= '9')) {
					throw new DbToolException(c[0] + ":属性名定义不合法");
				} else {
					if (c[1].trim().matches("int")) {
						table.getMp().put(c[0].trim(), "int");
					} else if (c[1].startsWith("char") || c[1].startsWith("varchar")) {
						table.getMp().put(c[0].trim(), c[1].trim());
					} else {
						throw new DbToolException(c[1] + ":属性类型不合法！");
					}
				}
			}
		}
		return table;
	}

    public boolean checkInsert(){
	    return checkInsert(true);
    }

	// 暂时没有主键、外键、索引（没有设计B+树，索引就不要想了，除非你重构整个文本数据库，不然永远都不会有的 —— 重构还不如重新设计一个O(∩_∩)O）
	public boolean checkInsert(boolean isCommit){
		String[] words = this.comm.split("\\s");
		if(words[0].toLowerCase().equals("insert")&&words[1].toLowerCase().equals("into")) {
            InsertInfo insertInfo = getInsertInfo();
			if(isCommit) {
                commit(Collections.singletonList(insertInfo));
            }
		} else {
			throw new DbToolException("无法解析插入命令！");
		}
		return true;
	}

    // 当前只支持and条件删除
	public int checkDel(){
		int num=0;
		String[] w = comm.split("\\s");
		if(!w[0].trim().toLowerCase().equals("delete")) {
			throw new DbToolException("解析语句错误!删除失败！");
		} else if(!w[1].trim().toLowerCase().equals("from")){
			throw new DbToolException("from缺失！删除失败！");
		}
		Tables tbs = Tools.readTables();
		if(w.length<3) {
			throw new DbToolException("参数不足！删除失败！");
		} else if(w.length==3){
			if(!tbs.getTables().contains(new Table(w[2].trim()))) {
				throw new DbToolException("表名错误！请检查！~");
			} else {
				File file = new File(w[2].trim()+".tb");
				if(file.exists()){
					file.delete();
				}
			}
		} else {
			if(!tbs.getTables().contains(new Table(w[2].trim()))) {
				throw new DbToolException("表名错误！请检查！~");
			}
			Table t = null;
			for (Table tb : tbs.getTables()) {
				if(tb.getName().equals(w[2].trim())) {
					t = tb;
					break;
				}
			}

			if(!w[3].trim().equals("where")) {
				throw new DbToolException("解析错误！");
			}
			String last = this.comm.split("where")[1].trim();
			String[] condition = last.split(" and ");
			int a[] = new int[t.getMp().size()+2];
			List<Object> ans = new ArrayList<Object>();
			for(int i=0;i<condition.length;i++) {
				String[] kv = condition[i].trim().split("=");
				if(kv.length!=2) {
					throw new DbToolException(condition[i]+"where条件解析错误，请检查！");
				}
				kv[0]= kv[0].trim();
				kv[1]= kv[1].trim();
				if(t.getMp().get(kv[0])==null) {
					throw new DbToolException(kv[0]+":列名不存在！");
				}
				if(t.getMp().get(kv[0]).equals("int")) {
					kv[1] = kv[1].replaceAll("'", "");
					if(!kv[1].matches("[\\-|0-9]+")) {
						throw new DbToolException(kv[1]+":与定义列的属性不符，请检查！");
					} else {
						Set s = t.getMp().keySet();
						Iterator it = s.iterator();
						int de=0;
						while(it.hasNext()){
							Object k = it.next();
							String u = (String)k;
							if(u.equals(kv[0])) {
								a[de]=1;
								ans.add(kv[1]);
								break;
							}
							de++;
						}
					}
				} else if(t.getMp().get(kv[0]).startsWith("varchar") || t.getMp().get(kv[0]).startsWith("char")) {
					if(!(kv[1].charAt(0)=='\''&&kv[1].charAt(kv[1].length()-1)=='\'')){
						throw new DbToolException(kv[1]+":与定义列的属性不符，请检查！");
					} else {
						kv[1]=kv[1].replace("'", "");
						Set s = t.getMp().keySet();
						Iterator it = s.iterator();
						int de=0;

						while(it.hasNext()){
							Object k = it.next();
							String u = (String)k;
							if(u.equals(kv[0])) {
								if(a[de]==1) {
									throw new DbToolException("解析错误！");
								}
								a[de]=1;
								ans.add(kv[1]);
								break;
							}
							de++;
						}
					}
				}
			}
			Data dt = Tools.readData(t.getName()+".tb");
			int [] stack = new int[333];
			int top=-1;
			for(int i=0;i<dt.getData().size();i+=t.getMp().size()) {
				int ind=0;
				int ret=0;
				for(int j=i;j<i+t.getMp().size();j++) {
					if(a[j-i]==1) {
						if(dt.getData().get(j) instanceof Integer) {
							if(((Integer)dt.getData().get(j)).equals(Integer.valueOf((String)ans.get(ind)))){
								ret++;
							}
						}
						if(dt.getData().get(j) instanceof String) {
							if(((String)(dt.getData().get(j))).equals((String)ans.get(ind))){
								ret++;
							}
						}
						ind++;
					}
				}
				if(ret==ans.size()){
					stack[++top] = i/t.getMp().size();
				}
			}
			int yu=0;
			num=(top+1);
			for(int i=0;i<dt.getData().size();i+=t.getMp().size()) {
				if(yu<=top&&stack[yu]==(int)(i/t.getMp().size())) {
					for(int j=i;j<i+t.getMp().size();j++){
						dt.getData().set(j, null);
					}
					yu++;
				}
			}
			List<Integer> nullArr = new ArrayList<Integer>();
			nullArr.add(null);
			dt.getData().removeAll(nullArr);
			Tools.writeData(dt,t.getName()+".tb");
		}
		return num;
	}

    // 当前只支持and条件查询、like查询（暂不关注%）、in查询，不支持order by、group by、limit、or
	public SelectRes checkSelect(){
        SelectRes selectRes = new SelectRes();
		String[] w=comm.split("\\s");
		if(!w[0].trim().toLowerCase().equals("select")) {
			throw new DbToolException("解析错误，请检查！");
		}
        Map<String, Object> condition = null; // 下面获取到所有列信息的时候初始化查询条件
        String[] conditions = null;
		if(comm.contains(" where ")) {
            String last = this.comm.split("where")[1].trim();
            conditions = last.split(" and ");
        }
		if(w[1].trim().equals("*")){
			if(!w[2].trim().equals("from")) {
				throw new DbToolException("解析错误，请检查！");
			}
			if(!Tools.readTables().getTables().contains(new Table(w[3].trim()))) {
				throw new DbToolException(w[3]+":表不存在，请检查！");
			}

			Table tb = null;
			for (Table	t : Tools.readTables().getTables()) {
				if(t.getName().equals(w[3].trim())) {
					tb = t;
					break;
				}
			}
			Set<?> s = tb.getMp().keySet();
			Iterator<?> ii = s.iterator();
			selectRes.setTableName(w[3]);
			while(ii.hasNext()){
				Object k = ii.next();
				selectRes.getColumnNames().add((String)k);
			}
            if(conditions!=null && conditions.length>0){
                condition = getAddCondition(conditions, selectRes.getColumnNames());
            }
			Data dt = Tools.readData(w[3].trim()+".tb");
			if(dt == null) {
                selectRes.setTotal(0);
                return selectRes;
            }
            int size = dt.getData().size();
            Map<String, Object> line = new HashMap<>();
            int colunmIndex = 0;
            boolean qualify = true;
			for(int i=0;i<size;i++) {
			    String colunmName = selectRes.getColumnNames().get(colunmIndex++);
                Object data = dt.getData().get(i);
                if(data!=null && data.equals(ValCons.NULL_VAL)){
                    line.put(colunmName, null);
                }else {
                    line.put(colunmName, data);
                }
                qualify = qualify && compareCondition(colunmName, data, condition);
                if((i+1)%tb.getMp().size()==0&&i!=0) {
				    if(qualify) {
                        selectRes.getResults().add(line);
                    }
				    colunmIndex = 0;
					line = new HashMap<>();
					qualify = true;
				}
			}
			selectRes.setTotal(selectRes.getResults().size());
		} else {
			String[] cls = comm.split("from");
			if(cls.length<2) {
				throw new DbToolException("解析错误，缺失from！");
			}
			String tnm = cls[1].trim().split("\\s")[0].trim();
			Tables ts = Tools.readTables();
			if(!ts.getTables().contains(new Table(tnm))) {
				throw new DbToolException(tnm+":表不存在！");
			}
			Table t = new Table();
			cls[0] = cls[0].substring(7).trim();
			String[] p = cls[0].split(",");
			for(int i=0;i<ts.getTables().size();i++) {
				if(ts.getTables().get(i).getName().equals(tnm)) {
					t = ts.getTables().get(i);
					break;
				}
			}
			for (String aP : p) {
				if (t.getMp().get(aP.trim()) == null) {
					throw new DbToolException(":列名不存在，请检查！");
				}
			}
			Set<?> s = t.getMp().keySet();
			Iterator<?> ii = s.iterator();
			selectRes.setTableName(tnm);
			int[]  a = new int[22];
			int ui=0;
            List<String> allColumnNames = new ArrayList<>();
			while(ii.hasNext()){
				Object k = ii.next();
                allColumnNames.add((String)k);
				for (String aP : p) {
					if (aP.trim().equals((String) k)) {
					    selectRes.getColumnNames().add(aP.trim());
						a[ui] = 1;
						break;
					}
				}
				ui++;
			}
            if(conditions!=null && conditions.length>0){
                condition = getAddCondition(conditions, allColumnNames);
            }
			Data dt = Tools.readData(tnm.trim()+".tb");
			int size=dt.getData().size();
            Map<String, Object> line = new HashMap<>();
            int colunmIndex = 0;
            boolean qualify = true;
			for(int i=0;i<size;i++) {
                String colunmName = selectRes.getColumnNames().get(colunmIndex++);
                Object data = dt.getData().get(i);
                qualify = qualify && compareCondition(colunmName, data, condition);
				if(a[i%t.getMp().size()]==1){
                    if(data!=null && data.equals(ValCons.NULL_VAL)){
                        line.put(colunmName, null);
                    }else {
                        line.put(colunmName, data);
                    }
				}
				if((i+1)%t.getMp().size()==0&&i!=0) {
				    if(qualify) {
                        selectRes.getResults().add(line);
                    }
                    colunmIndex = 0;
                    line = new HashMap<>();
                    qualify = true;
				}
			}
		}
		return selectRes;
	}

    public int checkUpdate(){
		return 0;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

    private Map<String, Object> getAddCondition(String[] conditions, List<String> columnNames) {
        Map<String, Object> map = new HashMap<>();
        for(String condition : conditions){
            if(!condition.contains("=") && !condition.contains(" like ") && !condition.contains(" in ")){
                throw new DbToolException("无法解析查询条件："+condition);
            }
            if(condition.contains("=")) {
                String[] c = condition.split("=");
                if (c.length != 2 || !columnNames.contains(c[0].trim())) {
                    throw new DbToolException("无法解析查询条件：" + condition);
                }
                map.put(c[0].trim(), c[1].trim().replaceAll("'", ""));
            } else if(condition.contains(" like ")){
                String[] c = condition.split(" like ");
                if (c.length != 2 || !columnNames.contains(c[0].trim())) {
                    throw new DbToolException("无法解析查询条件：" + condition);
                }
                map.put(c[0].trim()+"_LIKE", c[1].trim().replaceAll("%", "").replaceAll("'", ""));
            } else if(condition.contains(" in ")){
                String[] c = condition.split(" in ");
                if (c.length != 2 || !columnNames.contains(c[0].trim())) {
                    throw new DbToolException("无法解析查询条件：" + condition);
                }
                String data = c[1].trim();
                String[] datas = data.substring(1, data.length()-1)
                        .replaceAll("'", "").split(",");
                map.put(c[0].trim()+"_IN", Arrays.asList(datas));
            }
        }
        return map;
    }

    private boolean compareCondition(String colunmName, Object data, Map<String, Object> condition) {
        boolean qualify = true;
        if(condition!=null && condition.get(colunmName)!=null
                && !condition.get(colunmName).equals(data)){
            qualify = false;
        }
        if(condition!=null && condition.get(colunmName+"_LIKE")!=null){
            if(data instanceof String){
                if(!((String) data).contains((String)condition.get(colunmName+"_LIKE"))){
                    qualify = false;
                }
            }else {
                throw new DbToolException("条件解析失败，like条件使用者必须为字符类型" + colunmName+"_LIKE");
            }
        }
        if(condition!=null && condition.get(colunmName+"_IN")!=null
                && !((List)condition.get(colunmName+"_IN")).contains(data)){
            qualify = false;
        }
        return qualify;
    }

    public InsertInfo getInsertInfo(){
        StringBuilder name = new StringBuilder();
        int index=0;
        for(int i=12;i<comm.length();i++) {
            if(comm.charAt(i)=='(') {
                name = new StringBuilder(name.toString().trim());
                index=i+1;
                break;
            }
            name.append(comm.charAt(i));
        }
        Table t = new Table();
        t.setName(name.toString());
        if(!Tools.readTables().getTables().contains(t)) {
            throw new DbToolException(name+":表名不存在，请检查！");
        }

        Tables tbs = Tools.readTables();
        Table table = null;
        for (Table tb : tbs.getTables()) {
            if(tb.getName().equals(name.toString())) {
                table = tb;
                break ;
            }
        }
        StringBuilder colm = new StringBuilder();
        for(int i=index;i<comm.length();i++) {
            if(comm.charAt(i)==')') {
                index= i+1;
                break;
            }
            colm.append(comm.charAt(i));
        }
        String[] cs = colm.toString().split(",");

        StringBuilder val = new StringBuilder();
        for(int i=index;i<comm.length();i++) {
            if(comm.charAt(i)=='(') {
                index=i+1;
                break;
            }
            val.append(comm.charAt(i));
        }
        val = new StringBuilder(val.toString().trim());
        if(!val.toString().toLowerCase().equals("values")){
            throw new DbToolException("解析错误！");
        }
        StringBuilder num= new StringBuilder();
        for(int i=index;i<comm.length();i++) {
            if(comm.charAt(i)==')' && comm.substring(i).indexOf('\'')==-1) {
                break;
            }
            num.append(comm.charAt(i));
        }
        String[] vals= num.toString().split(",");
        List<Object> va = new ArrayList<Object>();
        if(cs.length!=vals.length) {
            throw new DbToolException("插入的值不对应请检查！");
        }
        for(int i=0;i<cs.length;i++) {
            String k = table != null ? table.getMp().get(cs[i].trim()) : null;
            if(null==k){
                throw new DbToolException(cs[i]+":属性不存在，请检查！");
            } else {
                if(k.equals("int")) {
					vals[i] = vals[i].replaceAll("'", "");
                    if(!vals[i].trim().matches("[\\-|0-9]+")) {
                        throw new DbToolException(vals[i].trim()+":插入的值与表定义的类型不一致！插入失败");
                    }
                    va.add(Integer.valueOf(vals[i]));
                } else if(k.startsWith("char") || k.startsWith("varchar")) {
                    vals[i]=vals[i].trim();
                    if(vals[i].charAt(0)!='\''||vals[i].charAt(vals[i].length()-1)!='\'') {
                        throw new DbToolException(vals[i].trim()+":插入的值与表定义的类型不一致！插入失败");
                    }
                    va.add(vals[i]);
                }
            }
        }
        InsertInfo res = new InsertInfo();
        res.setName(name.toString());
        res.setCs(cs);
        res.setVa(va);
        return res;
    }

	/**
	 * 提交
	 */
	public static void commit(List<InsertInfo> insertInfos){
	    String name = insertInfos.get(0).getName();
        Data dt = Tools.readData(name + ".tb");
        for(InsertInfo insertInfo : insertInfos) {
            String[] cs = insertInfo.getCs();
            List <Object> va = insertInfo.getVa();
            if (dt == null) {
                dt = new Data();
            }
            List<Table> tbs2 = Tools.readTables().getTables();
            Table tbn = null;
            for (Table tb2 : tbs2) {
                if (tb2.getName().equals(name)) {
                    tbn = tb2;
                    break;
                }
            }

            Set s = tbn.getMp().keySet();
            Iterator i = s.iterator();

            while (i.hasNext()) {
                Object k = i.next();
                for (int j = 0; j < cs.length; j++) {
                    if (cs[j].trim().equals(k)) {
                        if (va.get(j) instanceof String) {
                            String oi = (String) (va.get(j));
                            oi = oi.replace("'", "");
                            dt.getData().add(oi);
                        } else {
                            dt.getData().add(va.get(j));
                        }
                        break;
                    }
                }
            }
        }
		Tools.writeData(dt, name+".tb");
	}
}
