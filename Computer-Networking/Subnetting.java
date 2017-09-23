import java.util.*;
class Subnetting {
    public static void dispstartend(int ip[],int mask) {
        int i; int ip1[]=new int[4];
        for(i=0;i<4;i++) ip1[i]=ip[i];
        if(ip[0]>=0 && ip[0]<=127) System.out.println("Class A");
        else if(ip[0]>=128 && ip[0]<=191) System.out.println("Class B");
        else if(ip[0]>=192 && ip[0]<=223) System.out.println("Class C");
        else if(ip[0]>=224 && ip[0]<=239) System.out.println("Class D");
        else if(ip[0]>=240 && ip[0]<=255) System.out.println("Class E");
        char temp[],temp1[],temp2[],temp3[],temp4[],temp5[],temp6[],temp7[];
        char zero[]={'0','0','0','0','0','0','0','0'};
        char one[]={'1','1','1','1','1','1','1','1'};
        //System.out.println(new String(zero));
        temp=Integer.toBinaryString(ip[0]).toCharArray();
        temp1=Integer.toBinaryString(ip[1]).toCharArray();
        temp2=Integer.toBinaryString(ip[2]).toCharArray();
        temp3=Integer.toBinaryString(ip[3]).toCharArray();
        //System.out.println(ip1[3]);
        if(mask>=24) {
            for(i=temp3.length-1;i>=(temp3.length<32-mask?0:temp3.length-32+mask);i--) {
                temp3[i]='0';
            }
            ip[3]=Integer.parseInt(new String(temp3),2);
        }
        else if(mask<=23 && mask>=16) {
            for(i=temp2.length-1;i>=(temp2.length<32-mask-8?0:temp2.length-32+mask+8);i--) {
                temp2[i]='0';
            }
            ip[3]=Integer.parseInt(new String(zero),2);
            ip[2]=Integer.parseInt(new String(temp2),2);
        }
        else if(mask<=15 && mask>=8) {
            for(i=temp1.length-1;i>=(temp1.length<32-mask-16?0:temp1.length-32+mask+16);i--) {
                temp1[i]='0';
            }
            ip[3]=Integer.parseInt(new String(zero),2);
            ip[2]=Integer.parseInt(new String(zero),2);
            ip[1]=Integer.parseInt(new String(temp1),2);
        }
        else if(mask<=7 && mask>=0) {
            for(i=temp.length-1;i>=(temp.length<32-mask-24?0:temp.length-32+mask+24);i--) {
                temp[i]='0';
            }
            ip[3]=Integer.parseInt(new String(zero),2);
            ip[2]=Integer.parseInt(new String(zero),2);
            ip[1]=Integer.parseInt(new String(zero),2);
            ip[0]=Integer.parseInt(new String(temp),2);
        }
        System.out.print("Starting address: ");
        for(i=0;i<4;i++) System.out.print(ip[i]+"."); //System.out.println();
        String a=Integer.toBinaryString(ip1[0]),b=Integer.toBinaryString(ip1[1]);
        String c=Integer.toBinaryString(ip1[2]),d=Integer.toBinaryString(ip1[3]);
        for(i=0;i<8-d.length()+i;i++) { d="0"+d; }
        for(i=0;i<8-c.length()+i;i++) { c="0"+c; }
        for(i=0;i<8-b.length()+i;i++) { b="0"+b; }
        for(i=0;i<8-a.length()+i;i++) { a="0"+a; }
        temp4=a.toCharArray();
        temp5=b.toCharArray();
        temp6=c.toCharArray();
        temp7=d.toCharArray();
        //System.out.println(d);
        if(mask>=24) {
            for(i=temp7.length-1;i>=temp7.length-32+mask;i--) {
                temp7[i]='1';
                //for(int j=0;j<8;j++) System.out.print(temp7[j]);
                //System.out.println();
            }
            ip1[3]=Integer.parseInt(new String(temp7),2);
            //System.out.println(temp7.length-32+mask);
        }
        else if(mask<=23 && mask>=16) {
            for(i=temp6.length-1;i>=temp6.length-32+mask+8;i--) {
                temp6[i]='1';
            }
            ip1[3]=Integer.parseInt(new String(one),2);
            ip1[2]=Integer.parseInt(new String(temp6),2);
        }
        else if(mask<=15 && mask>=8) {
            for(i=temp5.length-1;i>=temp5.length-32+mask+16;i--) {
                temp5[i]='1';
            }
            ip1[3]=Integer.parseInt(new String(one),2);
            ip1[2]=Integer.parseInt(new String(one),2);
            ip[1]=Integer.parseInt(new String(temp5),2);
        }
        else if(mask<=7 && mask>=0) {
            for(i=temp4.length-1;i>=temp4.length-32+mask+24;i--) {
                temp4[i]='1';
            }
            ip1[3]=Integer.parseInt(new String(one),2);
            ip1[2]=Integer.parseInt(new String(one),2);
            ip1[1]=Integer.parseInt(new String(one),2);
            ip1[0]=Integer.parseInt(new String(temp4),2);
        }
        System.out.print("\nEnding address: ");
        for(i=0;i<4;i++) System.out.print(ip1[i]+".");
        
        
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter an IPv4 address: ");
        String ip=s.next();
        int mask,i,ip1[]=new int[4],j=0,beg=0,ns;
        System.out.print("Enter mask: "); mask=s.nextInt();
        boolean flag=true;
        for(i=0;i<ip.length();i++) {
            if(ip.charAt(i)=='.') {
                String temp=ip.substring(beg,i);
                beg=i+1;
                ip1[j]=Integer.parseInt(temp); j++;
                if(ip1[j-1] > 255) { flag=false; break; }
            }
        }
        ip1[3]=Integer.parseInt(ip.substring(beg,i));
        if(flag==false) System.out.println("Invalid IP address");
        else {
            dispstartend(ip1,mask);
            System.out.print("\nEnter no. of subnets: "); ns=s.nextInt();
            if(mask<24) System.out.println("This program will display subnets only for IP addresses with masks greater than 24");
            else {
                int total_add=(int)Math.pow(2,32-mask);
                int sub_add=total_add/ns,add;
                char temp[]=Integer.toBinaryString(ip1[3]).toCharArray();
                for(i=temp.length-1;i>=(temp.length<32-mask?0:temp.length-32+mask);i--) {
                    temp[i]='0';
                }
                ip1[3]=Integer.parseInt(new String(temp),2);
                add=ip1[3];
                for(i=0;i<ns;i++) {
                    System.out.println("Subnet "+(i+1));
                    System.out.print("Starting address: ");
                    for(j=0;j<3;j++) System.out.print(ip1[j]+".");
                    System.out.print(add);
                    System.out.print("\nEnding address: ");
                    for(j=0;j<3;j++) System.out.print(ip1[j]+".");
                    System.out.println(add + sub_add - 1);
                    add+=sub_add;
                }
                double m1=(double)Math.log(sub_add)/Math.log(2);
                System.out.println("Mask for each subnet: "+(int)(32-m1));
            }
        }
    }
}