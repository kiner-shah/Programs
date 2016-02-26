import java.util.*;
class Banker1 {
    public static void main(String[] args) {
        int available[],max[][],allocation[][],need[][];
        int i,j,n,m,k;
        boolean finish[];
        Scanner s = new Scanner(System.in);
        System.out.print("Enter total no. of processes: "); n=s.nextInt();
        System.out.print("Enter total no. of resources: "); m=s.nextInt();
        available=new int[m]; finish=new boolean[n];
        Arrays.fill(finish, false);
        max=new int[n][m]; allocation=new int[n][m]; need=new int[n][m];
        System.out.println("Enter Allocation matrix");
        for(i=0;i<n;i++) {
            for(j=0;j<m;j++) allocation[i][j]=s.nextInt();
        }
        System.out.println("Enter Max matrix");
        for(i=0;i<n;i++) {
            for(j=0;j<m;j++) max[i][j]=s.nextInt();
        }
        System.out.println("Enter Available matrix");
        for(i=0;i<m;i++) available[i]=s.nextInt();
        System.out.println("Need matrix is");
        for(i=0;i<n;i++) {
            for(j=0;j<m;j++) { 
                need[i][j]=max[i][j]-allocation[i][j];
                System.out.print(need[i][j]+" ");
            } 
            System.out.println();
        }
        Vector<Integer> seq=new Vector<Integer>();
        int temp[]=new int[m];
        while(true) {
            System.arraycopy(available, 0, temp, 0, m);
            for(i=0;i<n;i++) {
                if(finish[i]) continue;
                j=0;
                while(j<m && need[i][j]<=available[j]) j++;
                if(j==m) {
                    finish[i]=true;
                    for(k=0;k<m;k++) available[k]+=allocation[i][k];
                    seq.addElement(i+1);
                }
            }
            if(Arrays.equals(temp, available)) break;
            else System.arraycopy(available, 0, temp, 0, m);
        }
        if(seq.size() == n) {
            System.out.print("Safe sequence: ");
            Enumeration e=seq.elements();
            while(e.hasMoreElements()) System.out.print(e.nextElement()+",");
        }
        else System.out.println("Unsafe state");
    }
}
/*
Enter total no. of processes: 5
Enter total no. of resources: 4
Enter Allocation matrix
1 0 0 4
1 4 2 2
0 0 1 2
0 2 1 0
0 6 3 2
Enter Max matrix
1 6 5 6
2 4 5 7
0 0 1 2
1 7 5 0
0 6 5 2
Enter Available matrix
1 5 2 0
Need matrix is
0 6 5 2 
1 0 3 5 
0 0 0 0 
1 5 4 0 
0 0 2 0 
Safe sequence: 3,5,1,2,4,
*/