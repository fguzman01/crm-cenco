"""Tests for customer model"""

import unittest
from datetime import datetime
from crm.models import Customer


class TestCustomerModel(unittest.TestCase):
    """Test cases for Customer model"""
    
    def test_customer_creation(self):
        """Test creating a customer"""
        customer = Customer(
            id=1,
            name="John Doe",
            email="john@example.com",
            phone="+1234567890",
            company="Acme Corp"
        )
        
        self.assertEqual(customer.id, 1)
        self.assertEqual(customer.name, "John Doe")
        self.assertEqual(customer.email, "john@example.com")
        self.assertEqual(customer.phone, "+1234567890")
        self.assertEqual(customer.company, "Acme Corp")
        self.assertIsNotNone(customer.created_at)
        self.assertIsNotNone(customer.updated_at)
    
    def test_customer_without_company(self):
        """Test creating a customer without company"""
        customer = Customer(
            id=None,
            name="Jane Doe",
            email="jane@example.com",
            phone="+0987654321"
        )
        
        self.assertIsNone(customer.id)
        self.assertEqual(customer.name, "Jane Doe")
        self.assertIsNone(customer.company)
    
    def test_customer_to_dict(self):
        """Test converting customer to dictionary"""
        customer = Customer(
            id=1,
            name="John Doe",
            email="john@example.com",
            phone="+1234567890",
            company="Acme Corp"
        )
        
        customer_dict = customer.to_dict()
        
        self.assertEqual(customer_dict['id'], 1)
        self.assertEqual(customer_dict['name'], "John Doe")
        self.assertEqual(customer_dict['email'], "john@example.com")
        self.assertEqual(customer_dict['phone'], "+1234567890")
        self.assertEqual(customer_dict['company'], "Acme Corp")
        self.assertIsNotNone(customer_dict['created_at'])
        self.assertIsNotNone(customer_dict['updated_at'])
    
    def test_customer_from_dict(self):
        """Test creating customer from dictionary"""
        data = {
            'id': 1,
            'name': "John Doe",
            'email': "john@example.com",
            'phone': "+1234567890",
            'company': "Acme Corp",
            'created_at': datetime.now().isoformat(),
            'updated_at': datetime.now().isoformat()
        }
        
        customer = Customer.from_dict(data)
        
        self.assertEqual(customer.id, 1)
        self.assertEqual(customer.name, "John Doe")
        self.assertEqual(customer.email, "john@example.com")
        self.assertIsInstance(customer.created_at, datetime)


if __name__ == '__main__':
    unittest.main()
