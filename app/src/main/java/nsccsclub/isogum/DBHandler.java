package nsccsclub.isogum;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csconway on 4/21/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
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
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FUNCTIONS_TABLE = "CREATE TABLE " + TABLE_FUNCTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_FUNCTION + " TEXT" + ")";
        String CREATE_VARIABLES_TABLE = "CREATE TABLE " + TABLE_FUNCTIONS + "("
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VARIABLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUNCTIONS);
        //create tables again
        onCreate(db);
    }

    //CRUD METHODS FUNCTIONS
    public void createFunction(Function function){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, function.getName());
        contentValues.put(KEY_FUNCTION, function.getFunction());

        db.insert(TABLE_FUNCTIONS, null, contentValues);
        db.close();
    }

    public Function getFunction(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FUNCTIONS, new String[]{ KEY_ID,
        KEY_NAME, KEY_FUNCTION}, KEY_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,null,null,null);
        if (cursor!= null) {
            cursor.moveToFirst();
        }
        Function function = new Function(cursor.getString(1),
                cursor.getString(2), Integer.parseInt(cursor.getString(0)));

        db.close();
        cursor.close();
        return function;
    }

    public List<Function> getAllFunctions(){
        List<Function> list = new ArrayList<Function>();

        //select all from database
        String selectQuery = "SELECT * FROM" + TABLE_FUNCTIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Function function;

        //loop through cursor entries and add to list
        if (cursor.moveToFirst()){
            do{
                function = new Function(cursor.getString(1),
                        cursor.getString(2), Integer.parseInt(cursor.getString(0)));
                list.add(function);
            } while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }

    public int updateFunction(Function function){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_NAME, function.getName());
        contentValues.put(KEY_FUNCTION, function.getFunction());

        int id = db.update(TABLE_FUNCTIONS, contentValues, KEY_ID + " = ?",
                new String[] {String.valueOf(function.getId())});
        db.close();
        return id;
    }

    public void deleteFunction(Function function){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FUNCTIONS, KEY_ID + " = ?",
                new  String[] { String.valueOf(function.getId())});
        db.close();
    }

    public boolean isDuplicateFunction(Function function){
        //select all from database
        String selectQuery = "SELECT * FROM" + TABLE_FUNCTIONS;
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


    //CRUD METHODS VARIABLES
    public void createVariable(Variable variable){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, variable.getName());
        contentValues.put(KEY_VALUE, variable.getValue());
        contentValues.put(KEY_UNCERTAINTY, variable.getUncertainty());

        db.insert(TABLE_VARIABLES, null, contentValues);
        db.close();
    }

    public Variable getVariable(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VARIABLES, new String[]{ KEY_ID,
                        KEY_NAME, KEY_VALUE, KEY_UNCERTAINTY}, KEY_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,null,null,null);
        if (cursor!= null) {
            cursor.moveToFirst();
        }
        Variable variable = new Variable(cursor.getString(1),
                Double.parseDouble(cursor.getString(2)),Double.parseDouble(cursor.getString(3)), Integer.parseInt(cursor.getString(0)));

        db.close();
        cursor.close();
        return variable;
    }


}
