import java.sql.*;

public class Movies {
    private Connection connect() 
    {
        Connection c=null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            c=DriverManager.getConnection("jdbc:sqlite:movies.db");
            c.setAutoCommit(false);
        }
        catch(SQLException | ClassNotFoundException e)
        {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public void createTable() 
    {
        String cr="CREATE TABLE MOVIES (MOVID INT PRIMARY KEY NOT NULL,"+"NAME TEXT NOT NULL,"+"ACTOR TEXT NOT NULL,"+"ACTRESS TEXT NOT NULL,"+"YEAR CHAR(4),"+"DIRNAME TEXT)";
        try
        {
            Connection c = this.connect();
            Statement stat =c.createStatement();
            stat.execute(cr);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void insert(int movid,String name,String actor,String actress,String year,String dir_name) 
    {
        String insr ="INSERT INTO MOVIES(MOVID,NAME,ACTOR,ACTRESS,YEAR,DIRNAME) VALUES(?,?,?,?,?,?)";
        try
        {
            Connection c=this.connect();

            PreparedStatement pstat=c.prepareStatement(insr);
            pstat.setInt(1,movid);
            pstat.setString(2,name);
            pstat.setString(3,actor);
            pstat.setString(4,actress);
            pstat.setString(5,year);
            pstat.setString(6,dir_name);
            pstat.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void allselect() 
    {

        try
        {
            Connection c =this.connect();
            Statement stat = c.createStatement();
            ResultSet res= stat.executeQuery("SELECT * FROM MOVIES;");
            while(res.next())
            {
                int id=res.getInt("MOVID");
                String movieName=res.getString("NAME");
                String actor=res.getString("ACTOR");
                String actress=res.getString("ACTRESS");
                String year=res.getString("YEAR");
                String dir_name=res.getString("DIRNAME");

                System.out.println("MOVIE ID = "+id);
                System.out.println("MOVIE NAME = "+movieName);
                System.out.println("ACTOR NAME = "+actor);
                System.out.println("ACTRESS NAME = "+actress);
                System.out.println("YEAR = "+year);
                System.out.println("Director Name = "+dir_name);
                System.out.println();
            }
            res.close();
            stat.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void selectMovies() 
    {

        try
        {
            Connection c =this.connect();
            Statement stat = c.createStatement();
            ResultSet res= stat.executeQuery("SELECT NAME FROM MOVIES WHERE ACTOR='RAJ B SHETTY';");
            System.out.println("The movies in which RAJ B SHETTY acted are: ");
            while(res.next())
            {
                String movieName=res.getString("NAME");
                System.out.println(movieName);
            }
            res.close();
            stat.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }


    public static void main(String[] args) {
        Movies a =new Movies(); //creating an object of the class Movies
        a.createTable();
        a.insert(001,"GARUDA GAMANA","RAJ B SHETTY","-","2021","Midhun Mukundan");
        a.insert(002,"RAJAKUMARA","PUNITH RAJKUMAR","PRIYA ANAND","2017","Santhosh Ananddram");
        a.insert(003,"KGF CHAPTER 1","YASH","SHRINIDHI SHETTY","2018","PRASHANTH NEEL");
        a.insert(004,"KGF CHAPTER 2","YASH","SHRINIDHI SHETTY","2022","PRASHANTH NEEL");
        a.insert(005,"JAMES","PUNITH RAJKUMAR","PRIYA ANAND","2022","CHETAN KUMAR");
        a.insert(006,"ROBERT","DARSHAN","ASHA BHAT","2021","TARUN SUDHIR");
        a.insert(007,"DRISHYA 2","RAVICHANDRAN","NAVYA NAIR","2022","P. Vasu");
        a.selectall();
        a.selectMovies();
    }
}
