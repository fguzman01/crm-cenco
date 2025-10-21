"""Database operations for CRM system"""

import sqlite3
from datetime import datetime
from typing import List, Optional
from crm.models import Customer


class Database:
    """Database manager for CRM system using SQLite"""
    
    def __init__(self, db_path: str = "crm.db"):
        """Initialize database connection"""
        self.db_path = db_path
        self.connection = None
        self._connect()
        self._create_tables()
    
    def _connect(self):
        """Establish database connection"""
        self.connection = sqlite3.connect(self.db_path)
        self.connection.row_factory = sqlite3.Row
    
    def _create_tables(self):
        """Create necessary database tables"""
        cursor = self.connection.cursor()
        cursor.execute("""
            CREATE TABLE IF NOT EXISTS customers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                phone TEXT NOT NULL,
                company TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """)
        self.connection.commit()
    
    def close(self):
        """Close database connection"""
        if self.connection:
            self.connection.close()
    
    def create_customer(self, customer: Customer) -> Customer:
        """Create a new customer in the database"""
        cursor = self.connection.cursor()
        cursor.execute("""
            INSERT INTO customers (name, email, phone, company, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?)
        """, (
            customer.name,
            customer.email,
            customer.phone,
            customer.company,
            customer.created_at.isoformat(),
            customer.updated_at.isoformat()
        ))
        self.connection.commit()
        customer.id = cursor.lastrowid
        return customer
    
    def get_customer(self, customer_id: int) -> Optional[Customer]:
        """Get a customer by ID"""
        cursor = self.connection.cursor()
        cursor.execute("SELECT * FROM customers WHERE id = ?", (customer_id,))
        row = cursor.fetchone()
        
        if row:
            return Customer(
                id=row['id'],
                name=row['name'],
                email=row['email'],
                phone=row['phone'],
                company=row['company'],
                created_at=datetime.fromisoformat(row['created_at']),
                updated_at=datetime.fromisoformat(row['updated_at'])
            )
        return None
    
    def get_all_customers(self) -> List[Customer]:
        """Get all customers from the database"""
        cursor = self.connection.cursor()
        cursor.execute("SELECT * FROM customers ORDER BY created_at DESC")
        rows = cursor.fetchall()
        
        customers = []
        for row in rows:
            customers.append(Customer(
                id=row['id'],
                name=row['name'],
                email=row['email'],
                phone=row['phone'],
                company=row['company'],
                created_at=datetime.fromisoformat(row['created_at']),
                updated_at=datetime.fromisoformat(row['updated_at'])
            ))
        return customers
    
    def update_customer(self, customer: Customer) -> bool:
        """Update an existing customer"""
        cursor = self.connection.cursor()
        customer.updated_at = datetime.now()
        cursor.execute("""
            UPDATE customers
            SET name = ?, email = ?, phone = ?, company = ?, updated_at = ?
            WHERE id = ?
        """, (
            customer.name,
            customer.email,
            customer.phone,
            customer.company,
            customer.updated_at.isoformat(),
            customer.id
        ))
        self.connection.commit()
        return cursor.rowcount > 0
    
    def delete_customer(self, customer_id: int) -> bool:
        """Delete a customer by ID"""
        cursor = self.connection.cursor()
        cursor.execute("DELETE FROM customers WHERE id = ?", (customer_id,))
        self.connection.commit()
        return cursor.rowcount > 0
    
    def search_customers(self, query: str) -> List[Customer]:
        """Search customers by name, email, or company"""
        cursor = self.connection.cursor()
        search_pattern = f"%{query}%"
        cursor.execute("""
            SELECT * FROM customers
            WHERE name LIKE ? OR email LIKE ? OR company LIKE ?
            ORDER BY created_at DESC
        """, (search_pattern, search_pattern, search_pattern))
        rows = cursor.fetchall()
        
        customers = []
        for row in rows:
            customers.append(Customer(
                id=row['id'],
                name=row['name'],
                email=row['email'],
                phone=row['phone'],
                company=row['company'],
                created_at=datetime.fromisoformat(row['created_at']),
                updated_at=datetime.fromisoformat(row['updated_at'])
            ))
        return customers
