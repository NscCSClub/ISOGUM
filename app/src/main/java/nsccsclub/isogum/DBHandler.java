package nsccsclub.isogum;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds general methods for storing and saving data, in the ISO GUM application.
 * Created by csconway on 4/21/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static final String DATABASE_NAME = "saved_data";
    // Contacts table name
    private static final String TABLE_VARIABLES = "variables";
    // Contacts table name
    private static final String TABLE_FUNCTIONS = "functions";
    //table collumn names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FUNCTION = "function";
    private static final String KEY_VALUE = "value";
    private static final String KEY_UNCERTAINTY = "uncertainty";


    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Generic constructor for the DBHandler should be used for most every general case
     * @param context The app holding the handler.
     */
    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //both string sql commands are used for creating the database
        String CREATE_FUNCTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FUNCTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_FUNCTION  + " TEXT"+")";
        String CREATE_VARIABLES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_VARIABLES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_VALUE + " REAL," +  KEY_UNCERTAINTY + " REAL" + ")";
        db.execSQL(CREATE_FUNCTIONS_TABLE);
        db.execSQL(CREATE_VARIABLES_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older tables if they exist
        switch (oldVersion) {
            case (1):
                //drop older tables if they exist
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_VARIABLES);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUNCTIONS);
                onCreate(db);
                break;
            case (2):
                //drop older tables if they exist
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_VARIABLES);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUNCTIONS);
                onCreate(db);
                break;
        }
        //create tables again

    }

    //CRUD METHODS FUNCTIONS

    /**
     * Creates a new function and stores it in the database.
     * <div>
     *     <h3>Preconditions</h3>
     *     <ul>
     *         <li>Name must be unique, check with the is duplicate name methods</li>
     *         <li>All data must be tested and valid</li>
     *     </ul>
     * </div>
     * @param function The new function to add.
     */
    public void createFunction(Function function){
        SQLiteDatabase db = this.getWritableDatabase();

        //get bundle of value sto add
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, function.getName());
        contentValues.put(KEY_FUNCTION, function.getFunction());

        db.insert(TABLE_FUNCTIONS, null, contentValues);
        db.close();
    }


    /**
     * Gives a function in the database.
     * @throws IllegalArgumentException illegal argument exception if the function is not in the database.
     * @param id The unique id of the function.
     * @return The desired function.
     */
    public Function getFunction(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FUNCTIONS, new String[]{KEY_ID,
                        KEY_NAME, KEY_FUNCTION}, KEY_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        Function function=null;
        if (cursor!= null) {
            cursor.moveToFirst();
        }else{
            return function;
        }
        function = new Function(cursor.getString(1),
                    cursor.getString(2),Long.parseLong(cursor.getString(0)));


        db.close();
        cursor.close();
        return function;
    }

    /**
     * Gives all of the functions in the database.
     * @return All existing functions in the database.
     */
    public List<Function> getAllFunctions(){
        List<Function> list = new ArrayList<Function>();

        //select all from database
        String selectQuery = "SELECT * FROM " + TABLE_FUNCTIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Function function;
        //loop through cursor entries and add to list
        if (cursor.moveToFirst()){
            do{
                function = new Function(cursor.getString(1),
                        cursor.getString(2), Long.parseLong(cursor.getString(0)));
                list.add(function);
            } while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }

    /**
     * Updates a function in the database.
     * <div>
     *     <h3>Preconditions</h3>
     *     <ul>
     *         <li>The function must exist.</li>
     *         <li>The function must have a valid database id.</li>
     *     </ul>
     * </div>
     * @param function The function to update
     * @return The id of the function.
     */
    public long updateFunction(Function function){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_NAME, function.getName());
        contentValues.put(KEY_FUNCTION, function.getFunction());

        long id = db.update(TABLE_FUNCTIONS, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(function.getId())});
        db.close();
        return id;
    }


    /**
     * Deletes a function in the database.
     * <div>
     *     <h3>Preconditions</h3>
     *     <ul>
     *         <li>The function must exist in the database.</li>
     *     </ul>
     * </div>
     * @param function The function to delete.
     */
    public void deleteFunction(Function function){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_FUNCTIONS, KEY_ID + " = ?",
                    new String[]{String.valueOf(function.getId())});
        }catch (Exception e){
            throw  new IllegalArgumentException("The function does not exist.");
        }
        db.close();
    }

    /**
     * Checks if the function has a duplicated name in the database.
     * @param function The function to check.
     * @return True for duplicate, false for unique.
     */
    public boolean isDuplicateFunction(Function function){
        //select all from database
        String selectQuery = "SELECT * FROM " + TABLE_FUNCTIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //iterate through names and check for duplicates
        if (cursor.moveToFirst()){
            do{
                if (function.getName().compareTo(cursor.getString(1))==0){
                    db.close();
                    cursor.close();
                    return true;
                }
            } while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return false;
    }

    /**
     * Finds a function by name in the database.
     * @param name The name of the function to search for.
     * @return The unique id of the function.
     */
    public long findFunctionByName(String name){
        String selectQuery = "SELECT * FROM " + TABLE_FUNCTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                if (cursor.getString(1).compareTo(name)==0){
                    long id = Long.parseLong(cursor.getString(0));
                    cursor.close();
                    db.close();
                    return id;
                }
            }while (cursor.moveToNext());
        }
        //we didn't find it
        return -1;
    }


    //CRUD METHODS VARIABLES

    /**
     * Creates a new variable in the database.
     * <div>
     *     <h3>Preconditions</h3>
     *     <ul>
     *         <li>The variable must have a unique name, use isDuplicate method in this class
     *         to check.</li>
     *         <li>The variable must be checked and tested to be valid.</li>
     *     </ul>
     * </div>
     * @param variable The variable to create.
     */
    public void createVariable(Variable variable){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, variable.getName());
        contentValues.put(KEY_VALUE, variable.getValue());
        contentValues.put(KEY_UNCERTAINTY, variable.getUncertainty());

        db.insert(TABLE_VARIABLES, null, contentValues);
        db.close();
    }

    /**
     * Fetches a variable stored in the database by database id.
     * <div>
     *     <h3>Preconditions</h3>
     *     <ul>
     *         <li>This must be a current valid database id.</li>
     *     </ul>
     * </div>
     * @param id The unique current database id of the variable.
     * @return The variable object, null if it does not exist.
     */
    public Variable getVariable(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VARIABLES, new String[]{KEY_ID,
                        KEY_NAME, KEY_VALUE, KEY_UNCERTAINTY}, KEY_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        Variable variable = null;
        if (cursor!= null) {
            cursor.moveToFirst();
            variable = new Variable(cursor.getString(1),
                    Double.parseDouble(cursor.getString(2)),Double.parseDouble(cursor.getString(3)),
                    Long.parseLong(cursor.getString(0)));

        }


        db.close();
        cursor.close();
        return variable;
    }

    /**
     * Returns a list of all variables currently stored in the database.
     * @return List of all current stored variables.
     */
    public List<Variable> getAllVariables(){
        List<Variable> list = new ArrayList<Variable>();

        //get all varaibles from database
        String selectQuery = "SELECT * FROM " + TABLE_VARIABLES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Variable variable;

        // loop through cursor and add entries to list
        if (cursor.moveToFirst()){
            do{
                variable = new Variable(cursor.getString(1),
                        Double.parseDouble(cursor.getString(2)),
                                Double.parseDouble(cursor.getString(3)),
                                        Long.parseLong(cursor.getString(0)));
                list.add(variable);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }

    /**
     * Updates a variable currently stored int the database.
     * <div>
     *     <h3>Preconditions</h3>
     *     <ul>
     *         <li>The variable must have a current database id, and must have been
     *         fetched from the database.</li>
     *         <li>The variable must exist in the databse.</li>
     *     </ul>
     * </div>
     * @param variable The variable to update.
     * @return The new database id of the stored object.
     */
    public long updateVariable(Variable variable){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_NAME, variable.getName());
        contentValues.put(KEY_VALUE, variable.getValue());
        contentValues.put(KEY_UNCERTAINTY, variable.getUncertainty());

        long id = db.update(TABLE_VARIABLES, contentValues, KEY_ID + " = ?",
                new String[] {String.valueOf(variable.getId())});
        db.close();
        return id;
    }

    /**
     * Deleste a variable currently stored in the database.
     * <div>
     *     <h3>Preconditions</h3>
     *     <ul>
     *         <li>The variable must have a current database id, and must have been
     *         fetched from the database.</li>
     *         <li>The variable must exist in the databse.</li>
     *     </ul>
     * </div>
     * @param variable The variable to delete.
     */
    public void deleteVariable(Variable variable){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VARIABLES, KEY_ID + " = ?",
                new String[] {String.valueOf(variable.getId())});
        db.close();
    }

    public boolean isDuplicateVariable(Variable variable){
        //select all from the  database
        String selectQuery = "SELECT * FROM " + TABLE_VARIABLES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //iteragte through names and check for duplicates
        if (cursor.moveToFirst()){
            do{
                if (variable.getName().compareTo(cursor.getString(1))==0){
                    db.close();
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return false;
    }

    /**
     * Gives the id of a variable stored in the database, based on unique name.
     * @param name The name of the varaible to find.
     * @return The id of the variable, -1 if not found.
     */
    public long findVariableByName(String name){
        String selectQuery = "SELECT * FROM " + TABLE_VARIABLES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                if (cursor.getString(1).compareTo(name)==0){
                    long id = Long.parseLong(cursor.getString(0));
                    cursor.close();
                    db.close();
                    return id;
                }
            }while (cursor.moveToNext());
        }
        //we didn't find it
        return -1;
    }
}
