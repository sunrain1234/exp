import java.util.*;
//add to test
//add to test second
//add to test third
//add to test first
public class lab1 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		ArrayList<String[]> staff=new ArrayList<String[]>();//���ڴ�ű��ʽ
		String input = in.nextLine();//��ȡ�û�����
		while (input.length()!=0)
		{
			if (input.charAt(0)=='!')//�����һ���ַ�Ϊ������Ϊ����
			{
				//System.out.println("�����������");
				if (input.startsWith("!d/d"))//���������������󵼺���derivative()
				{
//					System.out.println("�������������");
					if (input.length()==6&&Character.isLetter(input.charAt(5)))
					{
						char ch = input.substring(5).charAt(0);
						ArrayList<String[]> result=derivative(staff,ch);
//						display(staff);
						display(result);
					}
					else
						System.out.println("�����������");
				}
				else if (input.startsWith("!simplify"))//�������ֵ���������ֵ����simplify()
				{
//					System.out.println("���������ֵ����");
					if (input.length()==9) display(staff);
					else
					{
						String[] var = input.substring(9).trim().split(" ");
						ArrayList<String[]> result=simplify(staff,var);
						display(result);
					}
				}
				else//��������ǣ��򷵻������������
					System.out.println("������������");
			}
			else//����Ϊ���ʽ
			{
				if (isExpression(input))
				//�����ʽisExpression()
				{
					staff=Expression(input);//������ʽExpression
//					display(staff);
					System.out.println(input);
				}
				else
					System.out.println("������ʽ����");
			}
			input = in.nextLine();//��ȡ�û�����
		}
		in.close();
	}
	public static boolean isExpression(String input)
	{
		//�ϴζ�ȡ���ַ���־������0����ĸ1������-1
		int flag=-1;//��һ���ַ�ֻ�������ֻ���ĸ�����Ա���ϴ�Ϊ����
		for (int i=0;i<input.length();i++)
		{
			char ch = input.charAt(i);//Ҫ����ж�������������
			if (Character.isDigit(ch))
			{
				 if (flag==1)
					return false;
				 flag=0;
			}
			else if (Character.isLetter(ch))
			{
				if (flag!=-1)
					return false;
				flag=1;
			}
			else if (ch=='+'||ch=='*')
			{
				if (flag==-1)
					return false;
				flag=-1;
			}
			else
				return false;
		}
		//���ͨ��
		return true;
	}
	
	public static ArrayList<String[]> Expression(String input)
	{
		ArrayList<String[]> staff=new ArrayList<String[]>();
		//����ɶ�ά����
		for (String s:input.split("\\+"))
		{
				staff.add(s.split("\\*"));
		}
		return staff;
	}
	
	public static ArrayList<String[]> derivative(ArrayList<String[]> staff,char ch)
	{
		ArrayList<String[]> result=new ArrayList<String[]>();
		int flag=1;
		for (String[] s:staff)
		{
			int count=0;
			for (String e:s)
			{
				if (e.charAt(0)==ch)
					count++;
			}
			if (count!=0)
			{
				flag=0;
				String[] t=Arrays.copyOf(s, s.length);
				for (int i=0;i<t.length;i++)
				{
					if (t[i].charAt(0)==ch)
					{
						t[i]=String.valueOf(count);
						break;
					}
				}
				result.add(t);
			}
		}
		if (flag==1)
		{
			System.out.println("Error, no variable");
			return null;
		}
		return result;
	}
	
	public static ArrayList<String[]> simplify(ArrayList<String[]> staff,String[] var)
	{
		ArrayList<String[]> result=new ArrayList<String[]>();
		for (String[] s:staff)
		{
			String[] t=Arrays.copyOf(s, s.length);
			result.add(t);
		}
		for (int i=0;i<var.length;i++)
		{
			if (var[i].charAt(1)=='='&&Character.isLetter(var[i].charAt(0))&&isNumeric(var[i].substring(2)))
			{
				String ch=var[i].substring(0, 1);
				String val=var[i].substring(2);
				//��staff�����з���Ҫ�����Ŀ���и��ģ������µ�result
				for (String[] s:result)
				{
					for (int j=0;j<s.length;j++)
						if (s[j].equals(ch))
							s[j]=val;
				}
			}
			else
			{
				System.out.println("�����������");
				return null;
			}
		}
		return result;
	}
	
	public static void display(ArrayList<String[]> result)
	{
		if (result!=null)
		{
			//��
			for (int i=0;i<result.size();i++)
			{
				int product=1;
				ArrayList<String> temp=new ArrayList<String>();
				for (String e:result.get(i))
				{
					if (Character.isDigit(e.charAt(0)))
					{
						product*=Integer.parseInt(e);
					}
					else
					{
						temp.add(e);
					}
				}
				if (product!=1)
					temp.add(String.valueOf(product));
				String[] s=new String[temp.size()];
				temp.toArray(s);
				result.set(i, s);
			}
			int addition=0;
			for (int i=0;i<result.size();i++)
			{
				if (result.get(i).length==1&&isNumeric(result.get(i)[0]))
				{
					addition+=Integer.parseInt(result.get(i)[0]);
					result.remove(i);
					i--;
				}
			}
			if (addition!=0)
			{
				String[] t={String.valueOf(addition)};
				result.add(t);
			}
			//���
			String output="";
			for (String[] s:result)
			{
				for (String e:s)
					output=output+e+"*";
				output=output.substring(0, output.length()-1)+"+";
			}
			output=output.substring(0, output.length()-1);
			System.out.println(output);
		}
		
	}
	
	public static boolean isNumeric(String str)
	{
		for (int i = 0; i < str.length(); i++)
			if (!Character.isDigit(str.charAt(i)))
				return false;
		return true;
	}
	
}