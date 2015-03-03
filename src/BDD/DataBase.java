/*
 * Cette classe sert à créer la base de données et intéragir avec cette dernière.
 */

/***********************************************


ATTENTION : LA NOMENCLATURE N'EST PAS LA MÊME EN BDD ET EN Java !
BDD : file_path
Java : filePath


***********************************************/



package BDD;
import java.sql.*;


/**
 *
 * @author akervadec
 */
public class DataBase {

	public DataBase(){
		this.connexion();
	}


	/**
	* Etabli une connection avec la base de données dataBase.db
	*/
	public void connexion(){
	    Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");
	}

	/**
	* Exécute les requêtes SQL permettant de créer les tables dans la base de données dataBase.db
	*/
	public void createTables(){
            Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
	      System.out.println("[CreateTables]Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE Question ("+
					"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
					"content VARCHAR(255) NOT NULL,"+
					"id_video INTEGER NOT NULL,"+
					"id_audio INTEGER NOT NULL,"+
					"id_language INTEGER NOT NULL);"+
				"CREATE TABLE Video ("+
					"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
					"name VARCHAR(50) NOT NULL,"+
					"file_path VARCHAR(255) NOT NULL,"+
					"id_language INTEGER NOT NULL,"+
					"format VARCHAR(25));"+
				"CREATE TABLE Audio ("+
					"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
					"name VARCHAR(50) NOT NULL,"+
					"file_path VARCHAR(255) NOT NULL,"+
					"id_language INTEGER NOT NULL,"+
					"format VARCHAR(25));"+
				"CREATE TABLE Language ("+
					"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
					"name VARCHAR(25));";
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("[CreateTables]Tables created successfully");
	}

	/**
	* Ajoute une vidéo dans la base de données dataBase.db puis créé un objet correspondant
	* @param name Le nom de la vidéo à ajouter
	* @param file_path Le chemin sur le disque de la vidéo à ajouter
	* @param language La langue de la vidéo
	* @param format Le format de la vidéo
	*/
	public void addVideo(String name, String filePath, String format, String nameLanguage){
		Connection c = null;
	    PreparedStatement stmtLang = null;
	    PreparedStatement stmtAdd = null;
	    int idLang=0;
	    String query = new String("SELECT id FROM Language WHERE Language.name=?;");
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
	    	c.setAutoCommit(false);	//Mise en place de la transaction manuelle
	    	System.out.println("[addVideo]Opened database successfully");

	    	//Recherche de l'id de la langue de la vidéo

	    	stmtLang = c.prepareStatement(query);
	    	stmtLang.setString(1,nameLanguage);	//Ajout des paramètres (variables) "?" de la ligne d'avant.
	    	ResultSet rs = stmtLang.executeQuery();
	    	while(rs.next()){
				idLang = rs.getInt("id");
			}

			//Ajout de la vidéo

			query = "INSERT INTO Video(name,file_path,id_language,format) VALUES (?,?,?,?);";
	    	stmtAdd = c.prepareStatement(query);
	    	//Paramétrage des variables de requête
	    	stmtAdd.setString(1,name);
	    	stmtAdd.setString(2,filePath);
	    	stmtAdd.setInt(3,idLang);
	    	stmtAdd.setString(4,format);
	    	stmtAdd.executeUpdate();

			rs.close();
			stmtLang.close();
	    	stmtAdd.close();
	    	c.commit();
	    	c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
		System.out.println("[addVideo]The video "+ name +"."+ format +" successfuly added to the DB");
	}


	/**
	*	Ajout d'un fichier audio dans la base de données dataBase.db
	*	@param name Le nom du fichier
	*	@param filePath Le chemin du fichier
	*	@param format Le format du fichier
	*	@param laguage La langue du fichier
	*/
	public void addAudio(String name, String filePath, String format, String nameLanguage){
		Connection c = null;
	    PreparedStatement stmtLang = null;
	    PreparedStatement stmtAdd = null;
	    int idLang=0;
	    String query = new String("SELECT id FROM Language WHERE Language.name=?;");
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
	    	c.setAutoCommit(false);	//Mise en place de la transaction manuelle
	    	System.out.println("[addAudio]Opened database successfully");

	    	//Recherche de l'id de la langue de la vidéo

	    	stmtLang = c.prepareStatement(query);
	    	stmtLang.setString(1,nameLanguage);	//Ajout des paramètres (variables) "?" de la ligne d'avant.
	    	ResultSet rs = stmtLang.executeQuery();
	    	while(rs.next()){
				idLang = rs.getInt("id");
			}

			//Ajout de la vidéo

			query = "INSERT INTO Audio(name,file_path,id_language,format) VALUES (?,?,?,?);";
	    	stmtAdd = c.prepareStatement(query);
	    	//Paramétrage des variables de requête
	    	stmtAdd.setString(1,name);
	    	stmtAdd.setString(2,filePath);
	    	stmtAdd.setInt(3,idLang);
	    	stmtAdd.setString(4,format);
	    	stmtAdd.executeUpdate();

			rs.close();
			stmtLang.close();
	    	stmtAdd.close();
	    	c.commit();
	    	c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
		System.out.println("[addAudio]The audio "+ name +"."+ format +" successfuly added to the DB");
	}

	/**
	*	Ajout d'une question dans la base de données dataBase.db, avec les objets Video et Audio connus et créés
	*	@param content La question (son contenu)
	*	@param video La vidéo correspondante à la réponse attendue
	*	@param audio L'audio correspondant à la réponse attendue
	*/
	public void addQuestion(String content, Video video, Audio audio, String nameLanguage){
		Connection c = null;
		PreparedStatement stmtLang = null;
		PreparedStatement stmtAdd = null;
	    int idLang=0;
		String query = new String("SELECT id FROM Language WHERE Language.name=?;");
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
			c.setAutoCommit(false);	//Mise en place de la transaction manuelle
			System.out.println("[addQuestion]Opened database successfully");

			stmtLang = c.prepareStatement(query);
	    	stmtLang.setString(1,nameLanguage);	//Ajout des paramètres (variables) "?" de la ligne d'avant.
	    	ResultSet rs = stmtLang.executeQuery();
	    	while(rs.next()){
				idLang = rs.getInt("id");
			}

			query = "INSERT INTO Question (content,id_video,id_audio,id_language) VALUES (?,?,?,?);";
			stmtAdd = c.prepareStatement(query);
			stmtAdd.setString(1, content);
			stmtAdd.setInt(2, video.getId());
			stmtAdd.setInt(3, audio.getId());
			stmtAdd.setInt(4, idLang);
			stmtAdd.executeUpdate();

			stmtLang.close();
			stmtAdd.close();
			c.commit();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("[addQuestion]The question \"" + content + "\" successfuly added.");
	}

	/**
	*	Ajout d'un langage dans la base de données dataBase.db
	*	@param name La langue en format String
	*/
	public void addLanguage(String name){
		Connection c = null;
		PreparedStatement stmt = null;
		String query = new String("INSERT INTO Language (name) VALUES (?);");
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
			c.setAutoCommit(false);	//Mise en place de la transaction manuelle
			System.out.println("[addLanguage]Opened database successfully");

			stmt = c.prepareStatement(query);
			stmt.setString(1, name);
			stmt.executeUpdate();

			stmt.close();
			c.commit();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("[addLanguage]The language " + name + " successfuly added.");
	}

	public Video manageVideo(Language language){
		Connection c = null;
		PreparedStatement stmt = null;
		Video result = new Video();
		int idLanguage = language.getId();
		String query = new String("SELECT * FROM Video WHERE id_language=? ORDER BY random() LIMIT 1;");
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
			c.setAutoCommit(false);
			System.out.println("[manageVideo]Opened database successfully");

			stmt = c.prepareStatement(query);
			stmt.setInt(1,idLanguage);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				result = new Video(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("file_path"),
					rs.getString("format"),
					rs.getInt("id_language"));
			}
			rs.close();
			stmt.close();
			c.close();
			return(result);
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("[manageVideo]Error");
		return(null);
	}

	public Audio manageAudio(Language language){
		Connection c = null;
		PreparedStatement stmt = null;
		Audio result = new Audio();
		int idLanguage = language.getId();
		String query = new String("SELECT * FROM Audio WHERE id_language=? ORDER BY random() LIMIT 1;");
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
			c.setAutoCommit(false);
			System.out.println("[manageAudio]Opened database successfully");

			stmt = c.prepareStatement(query);
			stmt.setInt(1,idLanguage);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				result = new Audio(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("file_path"),
					rs.getString("format"),
					rs.getInt("id_language"));
			}
			rs.close();
			stmt.close();
			c.close();
			return(result);
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("[manageAudio]Error");
		return(null);
	}

	public Question manageQuestion(Language language){
		Connection c = null;
		PreparedStatement stmt = null;
		Question result = new Question();
		int idLanguage = language.getId();
		String query = new String("SELECT * FROM Question WHERE id_language=? ORDER BY random() LIMIT 1;");
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
			c.setAutoCommit(false);
			System.out.println("[manageQuestion]Opened database successfully");

			stmt = c.prepareStatement(query);
			stmt.setInt(1,idLanguage);
			ResultSet rs = stmt.executeQuery();
			result = new Question(
				rs.getInt("id"),
				rs.getString("content"),
				rs.getInt("id_video"),
				rs.getInt("id_audio"),
				rs.getInt("id_language"));
			rs.close();
			stmt.close();
			c.close();
			return(result);
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("[manageQuestion]Error");
		return(null);
	}
}
