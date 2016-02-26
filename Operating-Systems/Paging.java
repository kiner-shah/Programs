/**
 *
 * @author KINER SHAH
 */
import java.util.*;
class Paging {
    public static void main(String[] args) {
        int page_t[][],i,j,npt,in,x,newf=4;
        Scanner s = new Scanner(System.in);
        System.out.print("Enter size of page table: ");
        npt=s.nextInt();
        page_t=new int[npt][2];
        page_t[0][0]=4; page_t[0][1]=13;
        page_t[1][0]=7; page_t[1][1]=14;
        page_t[2][0]=1; page_t[2][1]=1;
        page_t[3][0]=9; page_t[3][1]=2;
        System.out.print("Enter no. of pages to be processed: ");
        in=s.nextInt();
        System.out.println("Enter page numbers");
        for(i=0;i<in;i++) {
            x=s.nextInt();
            for(j=0;j<npt;j++) {
                if(page_t[j][0] == x)
                    break;
            }
            if(j < npt) System.out.print("\nHit occured for "+x+". Page: "+page_t[j][0]+" Frame: "+page_t[j][1]);
            else {
                int q=1;
                for(j=0;j<npt;j++) {
                    if(page_t[j][1] == q) q++;
                }
                System.out.print("\nPage no. not found. Fault occured for "+x);
                page_t[newf][0]=x; page_t[newf][1]=q; newf++;
                System.out.print(". Page: "+x+" Frame: "+page_t[newf-1][1]);
            }
        }
        System.out.println();
    }
}
/*
OUTPUT
Enter size of page table: 10
Enter no. of pages to be processed: 5
Enter page numbers
7 6 1 3 5

Hit occured for 7. Page: 7 Frame: 14
Page no. not found. Fault occured for 6. Page: 6 Frame: 3
Hit occured for 1. Page: 1 Frame: 1
Page no. not found. Fault occured for 3. Page: 3 Frame: 4
Page no. not found. Fault occured for 5. Page: 5 Frame: 5
*/