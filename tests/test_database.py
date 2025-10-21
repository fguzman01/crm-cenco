"""Tests for database operations"""

import unittest
import os
import tempfile
from crm.database import Database
from crm.models import Customer


class TestDatabase(unittest.TestCase):
    """Test cases for Database operations"""
    
    def setUp(self):
        """Set up test database"""
        self.temp_db = tempfile.NamedTemporaryFile(delete=False, suffix='.db')
        self.temp_db.close()
        self.db = Database(self.temp_db.name)
    
    def tearDown(self):
        """Clean up test database"""
        self.db.close()
        if os.path.exists(self.temp_db.name):
            os.unlink(self.temp_db.name)
    
    def test_create_customer(self):
        """Test creating a customer"""
        customer = Customer(
            id=None,
            name="John Doe",
            email="john@example.com",
            phone="+1234567890",
            company="Acme Corp"
        )
        
        created_customer = self.db.create_customer(customer)
        
        self.assertIsNotNone(created_customer.id)
        self.assertEqual(created_customer.name, "John Doe")
        self.assertEqual(created_customer.email, "john@example.com")
    
    def test_get_customer(self):
        """Test getting a customer by ID"""
        customer = Customer(
            id=None,
            name="Jane Doe",
            email="jane@example.com",
            phone="+0987654321"
        )
        created_customer = self.db.create_customer(customer)
        
        retrieved_customer = self.db.get_customer(created_customer.id)
        
        self.assertIsNotNone(retrieved_customer)
        self.assertEqual(retrieved_customer.name, "Jane Doe")
        self.assertEqual(retrieved_customer.email, "jane@example.com")
    
    def test_get_all_customers(self):
        """Test getting all customers"""
        customer1 = Customer(None, "John Doe", "john@example.com", "+1234567890")
        customer2 = Customer(None, "Jane Doe", "jane@example.com", "+0987654321")
        
        self.db.create_customer(customer1)
        self.db.create_customer(customer2)
        
        customers = self.db.get_all_customers()
        
        self.assertEqual(len(customers), 2)
    
    def test_update_customer(self):
        """Test updating a customer"""
        customer = Customer(
            id=None,
            name="John Doe",
            email="john@example.com",
            phone="+1234567890"
        )
        created_customer = self.db.create_customer(customer)
        
        created_customer.name = "John Smith"
        created_customer.phone = "+1111111111"
        
        result = self.db.update_customer(created_customer)
        
        self.assertTrue(result)
        
        updated_customer = self.db.get_customer(created_customer.id)
        self.assertEqual(updated_customer.name, "John Smith")
        self.assertEqual(updated_customer.phone, "+1111111111")
    
    def test_delete_customer(self):
        """Test deleting a customer"""
        customer = Customer(
            id=None,
            name="John Doe",
            email="john@example.com",
            phone="+1234567890"
        )
        created_customer = self.db.create_customer(customer)
        
        result = self.db.delete_customer(created_customer.id)
        
        self.assertTrue(result)
        
        deleted_customer = self.db.get_customer(created_customer.id)
        self.assertIsNone(deleted_customer)
    
    def test_search_customers(self):
        """Test searching customers"""
        customer1 = Customer(None, "John Doe", "john@example.com", "+1234567890", "Acme Corp")
        customer2 = Customer(None, "Jane Smith", "jane@example.com", "+0987654321", "TechCo")
        customer3 = Customer(None, "Bob Johnson", "bob@acme.com", "+1111111111", "Acme Corp")
        
        self.db.create_customer(customer1)
        self.db.create_customer(customer2)
        self.db.create_customer(customer3)
        
        # Search by company
        results = self.db.search_customers("Acme")
        self.assertEqual(len(results), 2)
        
        # Search by name
        results = self.db.search_customers("Jane")
        self.assertEqual(len(results), 1)
        self.assertEqual(results[0].name, "Jane Smith")
        
        # Search by email
        results = self.db.search_customers("@acme.com")
        self.assertEqual(len(results), 1)


if __name__ == '__main__':
    unittest.main()
