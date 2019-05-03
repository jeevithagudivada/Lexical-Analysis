package compiler;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Lexical extends javax.swing.JFrame {
    
    public Lexical() {
        
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        jLabel1.setText("   LEXICAL ANALYSER FOR C ");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jButton1.setText("PRODUCE TOKENS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(193, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(299, 299, 299))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1)
                .addGap(179, 179, 179)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(302, Short.MAX_VALUE))
        );
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        symbol s=new symbol();
        s.dispTokens(fl,lineNum);
        this.setVisible(false);
        new symbol().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    public static enum Type 
    {
        // This Scheme-like language has three token types:
        // open parens, close parens, and an "atom" type
        //LPAREN, RPAREN, ATOM;
        IDENTIFIER,NUMBER,OPERATOR,DELIMITER,KEY,STRINGLITERAL,CPP;
    }
    
    public static class Token 
    {
        public final Type t;
        public final String c; 
        public String v;
        public final int l;
        public String sc;
        public Token(Type t, String c,String v,int l) 
        {
            this.t = t;
            this.c = c;
            this.v = v;
            this.l = l;
            this.sc="";
        }
        public void display()
        {
            System.out.println(this.t+" "+this.c+" "+this.v+" "+this.l);
        }
    }
    
     public static String getAtom(String s, int i) 
     {
        int j = i;
        Character ch='_';
        for( ; j < s.length(); ) 
        {
            if(Character.isLetter(s.charAt(j))||Character.isDigit(s.charAt(j))||s.charAt(j)==ch) 
            {
                j++;
            } 
            else 
            {
                return s.substring(i, j);
            }
        }
        return s.substring(i, j);
    }
       
    public static boolean isNum(String s) 
    {    
        for(int j=0 ; j < s.length() ;++j ) 
        {
            if (!Character.isDigit(s.charAt(j)))
                return false;
        }
        return true;
    } 
    
    
    public static String recovery(String cond)
    {
        //for conditional if,while and for
        String forc[]={"fro","ofr","or","fr","fo"};
        List<String> ni=Arrays.asList(forc);
        String whc[]={"hwile","wihle","whlie","whiel","hile","wile","whle","whie","whil"};
        List<String> nl=Arrays.asList(whc);
        if(cond.equals("fi"))
        {
            cond="if";
        }
        if(ni.contains(cond))
        {
            cond="for";
        }
        if(nl.contains(cond))
        {
            cond="while";
        }
        return cond;
    }
    
    public static String scope="global";
    public static String keys[]={"main","break","case","char","do","continue","float","else","for","if","int","return","static","struct","switch","void","while"};
    public static String delimiters[]={":",";","{","}","(",")","[","]"};
    public static List<String> n=Arrays.asList(keys);
    public static List<String> m=Arrays.asList(delimiters);
    //public static List<Token> id=new ArrayList<Token>();
    
     public static List<Token> lex(String input,int l,List<Token> f) 
     {
        List<Token> result = new ArrayList<Token>();
        //System.out.println("jik");
        int ji=0;
        
        //System.out.println("jik1");
        if(input.charAt(ji)=='#')
        {
            //System.out.println("jik2");
            //int kp=0;
            /*if(input.charAt(input.length()-1)!='>')
            {
                System.out.println("Error Recovery:missing paranthesis "+'>'+" in line number "+l+" in scope "+scope+".");
                result.add(new Token(Type.CPP,input.substring(j,input.length())+Character.toString('>'),"",l));
                kp=1;
            }
            */
                result.add(new Token(Type.CPP,input.substring(ji,input.length()),"",l));
            
        }
        
        /*else if(input.charAt(ji)=='/')
        {
            //System.out.println("jik3");
            if(input.charAt(ji+1)=='/'){
                result.add(new Token(Type.COMMENT,input.substring(ji,input.length()),"",l));
            }
            else{
                System.out.println("Error recovery: One comment line missing in line num "+l+" in "+scope+" scope.");
                result.add(new Token(Type.COMMENT,Character.toString('/')+input.substring(ji,input.length()),"",l));
            }
        }  */    
        else
        {
        //System.out.println("jik4");
        for(int i = 0; i < input.length(); ) 
        {
            //System.out.println("keer");
            switch(input.charAt(i)) 
            {
                case ';':
                case ':':
                case '{':
                case '}':
                case '(':
                case ')':
                case '[':
                case ']':
                case ',':
                    result.add(new Token(Type.DELIMITER,Character.toString(input.charAt(i))," ",l));
                    i++;
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '%':
                    result.add(new Token(Type.OPERATOR,Character.toString(input.charAt(i))," ",l));
                    i++;
                    break;
                case '=':
                    if(input.charAt(i+1)=='='){
                        result.add(new Token(Type.OPERATOR,Character.toString(input.charAt(i))+Character.toString(input.charAt(i+1)),"",l));
                        ++i;
                    }
                    else
                        result.add(new Token(Type.OPERATOR,Character.toString(input.charAt(i)),"",l));
                    ++i;
                    break;
                
                case '>':
                    if(input.charAt(i+1)=='='){
                        result.add(new Token(Type.OPERATOR,Character.toString(input.charAt(i))+Character.toString(input.charAt(i+1)),"",l));
                        ++i;
                    }
                    else
                        result.add(new Token(Type.OPERATOR,Character.toString(input.charAt(i)),"",l));
                    ++i;
                    break;
                    
                case '<':
                    if(input.charAt(i+1)=='='){
                        result.add(new Token(Type.OPERATOR,Character.toString(input.charAt(i))+Character.toString(input.charAt(i+1)),"",l));
                        ++i;
                    }
                    else
                        result.add(new Token(Type.OPERATOR,Character.toString(input.charAt(i)),"",l));
                    ++i;
                    break;
                default: 
                    //String prev=null;
                    if(Character.isWhitespace(input.charAt(i))) 
                    {
                        i++;
                    }
                    else if(input.charAt(i)=='"')
                    {
                        int y=i;
                        int o=i+1;
                        for(;o<input.length();++o)
                        {
                            if(input.charAt(o)=='"')
                            {
                                i=++o;
                                break;
                            }
                        }
                        
                            result.add(new Token(Type.STRINGLITERAL,input.substring(y, i),"",l));
                    }
                    else
                    {
                        
                        
                        String atom = getAtom(input,i);
                        i+=atom.length();
                        //again check whether atom got is keyword,operator,id,number
                        //Arrays.asList(yourArray).contains(yourValue)
                        if(n.contains(atom))
                        {
                            if(atom.equals("else"))
                            {
                                String hh=getAtom(input,i);
                                if(hh.equals("if"))
                                {
                                    i+=atom.length();
                                    atom=atom+" "+hh;
                                }
                            }
                            if(atom.equals("main"))
                            {
                                scope="main";
                            }
                            result.add(new Token(Type.KEY,atom,"",l));
                            //recovery
                            if((atom.equals("break")||atom.equals("continue")||atom.equals("return"))&&input.charAt(input.length()-1)!=';')
                            {
                                System.out.println("Recovery:Missing semicolon in line "+l+" in scope "+scope);
                                input=input+Character.toString(';');
                            }
                            
                            
                        }
                        else if(isNum(atom))
                        {
                            result.add(new Token(Type.NUMBER,atom,"",l));
                            /*Token t3=result.get(result.size()-1);
                            if(t3.c=="="){
                                
                            }*/
                        }
                        
                        else
                        {
                            //add error recovery over here
                            
                            if(input.charAt(i)=='(')
                            {
                                String rec=atom;
                                if(result.size()==0)
                                {
                                    rec=recovery(atom);
                                }
                                int z=0;
                                if((input.charAt(input.length()-1)==';'))
                                {
                                    z=1;
                                    
                                }
                                else
                                {
                                    if((input.charAt(input.length()-1)!=')'))
                                    {
                                        input=input+Character.toString(')');
                                        System.out.println("Error Recovery:Missing paranthesis "+')'+" in line "+l+" and in scope "+scope);
                                    }
                                }
                                if(z==0)
                                {
                                if(rec.equals(atom))
                                {
                                    scope=atom;
                                    result.add(new Token(Type.IDENTIFIER,atom,"",l));
                                }
                                else
                                {
                                    result.add(new Token(Type.KEY,rec,"",l));
                                    System.out.println("Error Recovery: key error at line "+l+" and scope "+scope);
                                }
                                }
                            }
                            
                            else
                            {
                                //System.out.println("what?");
                                if(result.size()!=0)
                                {
                                    //System.out.println("what the");
                                    Token prev=result.get(result.size()-1);
                                    if(prev.t==Type.KEY)
                                    {
                                        //System.out.println("wh");
                                        Token newTok=new Token(Type.IDENTIFIER,atom,"",l);
                                        newTok.sc=scope;
                                        for(int k=i;k<input.length();)
                                        {
                                            //System.out.println("in for");
                                            while(input.charAt(k)==' ')
                                            {
                                                ++k;
                                                continue;
                                            }
                                            if(input.charAt(k)=='=')
                                            {
                                                String h=getAtom(input,k+1);
                                                newTok.v=h;
                                                System.out.println(h);
                                                //++k;
                                                break;
                                            }
                                            else
                                            {
                                                break;
                                            }
                                        }
                                        result.add(newTok);
                                        //System.out.println("gfh");
                                        if(input.charAt(input.length()-1)!=';'){
                                            input=input+Character.toString(';');
                                            System.out.println("Error Recovery:Missing semicolon in declaration in line "+l+" in scope "+scope);
                                        }
                                    }
                                   
                                }
                                else
                                {
                                    //System.out.println("ydhc");
                                    Token newToks=new Token(Type.IDENTIFIER,atom,"",l);
                                    if(f.size()!=0)
                                    {
                                        int v=0;
                                        for(int d=(f.size()-1);d>=0;--d)
                                        {
                                            Token u=f.get(d);
                                            
                                            if(u.t==Type.IDENTIFIER&&((u.c).equals(atom))&&(u.sc==scope||u.sc=="global"))
                                            {
                                                int w=i;
                                                while(input.charAt(w)==' ')
                                                {
                                                    w++;
                                                    continue;
                                                }
                                                if(input.charAt(w)=='=')
                                                {
                                                    String h=getAtom(input,w+1);
                                                    u.v=h;
                                                    v=1;
                                                    break;
                                                }
                                                else
                                                {
                                                    break;
                                                }
                                            }
                                        }
                                        if(v==1&&input.charAt(input.length()-1)!=';'){
                                            input=input+Character.toString(';');
                                            System.out.println("Error Recovery:Missing semicolon in line "+l+" in scope "+scope);
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }
        }
        return result;
        
     }
    public static List<Token> fl=new ArrayList<Token>();
    public static int lineNum=0;
    public static void main(String args[]) throws IOException {
        
        BufferedReader br=new BufferedReader(new FileReader("C:/Users/jeevitha/Desktop/5th sem/5th sem/CD project/CD FINAL/input.txt"));
        String line=null;
        BufferedWriter bw=new BufferedWriter(new FileWriter("C:/Users/jeevitha/Desktop/5th sem/5th sem/CD project/CD FINAL/output.txt"));
        
        
        //System.out.println("in main");
        List<Token> t;
        int f=0;
        while((line=br.readLine())!=null)
        {
            ++lineNum;
            int ji=0;
            while(line.charAt(ji)==' ')
            {
                ++ji;
                //System.out.println("in space");
            }
        
            if(f==0&&line.charAt(ji)=='/')
            {
                //System.out.println("jik3");
                if(line.charAt(ji+1)=='/')
                {
                    bw.write("");
                    
                }
                else if(line.charAt(ji+1)=='*')
                {
                    f=1;
                    if(line.charAt(line.length()-2)=='*')
                    {
                        if(line.charAt(line.length()-1)=='/')
                            f=0;
                    }
                    else
                    {
                        bw.write(""); 
                    }
                    
                }
            }
            else if(f==1)
            {
                if(line.charAt(line.length()-2)=='*')
                {
                    if(line.charAt(line.length()-1)=='/')
                        f=0;
                }
                else
                {
                 bw.write(""); 
                }
            }
            else
            {
                bw.write(line);
                //bw.newLine();
                System.out.println("in line"+lineNum);
                t=lex(line,lineNum,fl);
                fl.addAll(t);
                System.out.println("hi");
            }
        }
        br.close();
        bw.close();
        //Set<Token> s = new LinkedHashSet<>(f);
        
        for(Token x : fl) 
        {
            x.display();
        }
        //System.out.println("after main");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lexical().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
