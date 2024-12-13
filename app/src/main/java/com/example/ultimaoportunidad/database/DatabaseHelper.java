package com.example.ultimaoportunidad.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ultimaoportunidad.models.Expense;
import com.example.ultimaoportunidad.models.Group;
import com.example.ultimaoportunidad.models.Member;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gestion_gastos.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    public static final String TABLE_USERS = "users";

    // Columns
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "passwd";

    // Create Table Query
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
            COLUMN_EMAIL + " TEXT UNIQUE, " +
            COLUMN_PASSWORD + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Insert User
    public boolean insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Check User Credentials
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public List<Group> getGroupsForUser(String username) {
        List<Group> groups = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT p.id_proj, p.name_proj, p.description " +
                "FROM projects p " +
                "INNER JOIN members m ON p.id_proj = m.id_proj " +
                "WHERE m.username = ?";

        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id_proj"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name_proj"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                groups.add(new Group(id, name, description));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return groups;
    }

    public boolean insertGroup(String groupName, String groupDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name_proj", groupName);
        values.put("description", groupDescription);

        long result = db.insert("projects", null, values);
        return result != -1;
    }

    public void deleteGroup(int groupId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("projects", "id_proj = ?", new String[]{String.valueOf(groupId)});
        db.close();
    }

    public List<Expense> getExpensesForGroup(int groupId) {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT name_payment, amount FROM payments WHERE id_proj = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(groupId)});

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name_payment"));
                float amount = cursor.getFloat(cursor.getColumnIndexOrThrow("amount"));
                expenses.add(new Expense(name, amount));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return expenses;
    }

    public float getGroupBalance(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(amount) as total FROM payments WHERE id_proj = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(groupId)});

        float balance = 0;
        if (cursor.moveToFirst()) {
            balance = cursor.getFloat(cursor.getColumnIndexOrThrow("total"));
        }

        cursor.close();
        return balance;
    }

    public boolean insertExpense(String name, float amount, String description, int groupId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name_payment", name);
        values.put("amount", amount);
        values.put("description", description);
        values.put("id_proj", groupId);

        long result = db.insert("payments", null, values);
        return result != -1;
    }

    public String getDebtsSummaryForGroup(int groupId) {
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder summary = new StringBuilder();

        String query = "SELECT username, SUM(amount) as total FROM payment_members pm " +
                "INNER JOIN payments p ON pm.id_payment = p.id_payments " +
                "WHERE p.id_proj = ? GROUP BY username";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(groupId)});

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                float amount = cursor.getFloat(cursor.getColumnIndexOrThrow("total"));
                summary.append(username).append(": $").append(String.format("%.2f", amount)).append("\n");
            } while (cursor.moveToNext());
        } else {
            summary.append("No hay deudas pendientes.");
        }

        cursor.close();
        return summary.toString();
    }

    public boolean settleDebtsForGroup(int groupId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete("payment_members", "id_payment IN (SELECT id_payments FROM payments WHERE id_proj = ?)", new String[]{String.valueOf(groupId)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public List<Member> getMembersForGroup(int groupId) {
        List<Member> members = new ArrayList<Member>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT username FROM members WHERE id_proj = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(groupId)});

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                members.add(new Member(username));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return members;
    }
}