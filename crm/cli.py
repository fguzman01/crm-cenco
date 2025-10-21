"""Command-line interface for CRM system"""

import sys
from crm.database import Database
from crm.models import Customer


class CRM:
    """CRM command-line interface"""
    
    def __init__(self, db_path: str = "crm.db"):
        """Initialize CRM with database"""
        self.db = Database(db_path)
    
    def close(self):
        """Close database connection"""
        self.db.close()
    
    def add_customer(self, name: str, email: str, phone: str, company: str = None):
        """Add a new customer"""
        customer = Customer(
            id=None,
            name=name,
            email=email,
            phone=phone,
            company=company
        )
        customer = self.db.create_customer(customer)
        print(f"✓ Customer added successfully! ID: {customer.id}")
        return customer
    
    def list_customers(self):
        """List all customers"""
        customers = self.db.get_all_customers()
        if not customers:
            print("No customers found.")
            return
        
        print(f"\nTotal customers: {len(customers)}\n")
        print("-" * 80)
        for customer in customers:
            print(f"ID: {customer.id}")
            print(f"Name: {customer.name}")
            print(f"Email: {customer.email}")
            print(f"Phone: {customer.phone}")
            if customer.company:
                print(f"Company: {customer.company}")
            print(f"Created: {customer.created_at.strftime('%Y-%m-%d %H:%M:%S')}")
            print("-" * 80)
    
    def get_customer(self, customer_id: int):
        """Get customer details by ID"""
        customer = self.db.get_customer(customer_id)
        if not customer:
            print(f"Customer with ID {customer_id} not found.")
            return None
        
        print(f"\nCustomer Details:")
        print(f"ID: {customer.id}")
        print(f"Name: {customer.name}")
        print(f"Email: {customer.email}")
        print(f"Phone: {customer.phone}")
        if customer.company:
            print(f"Company: {customer.company}")
        print(f"Created: {customer.created_at.strftime('%Y-%m-%d %H:%M:%S')}")
        print(f"Updated: {customer.updated_at.strftime('%Y-%m-%d %H:%M:%S')}")
        return customer
    
    def update_customer(self, customer_id: int, name: str = None, 
                       email: str = None, phone: str = None, company: str = None):
        """Update customer information"""
        customer = self.db.get_customer(customer_id)
        if not customer:
            print(f"Customer with ID {customer_id} not found.")
            return False
        
        if name:
            customer.name = name
        if email:
            customer.email = email
        if phone:
            customer.phone = phone
        if company is not None:
            customer.company = company
        
        if self.db.update_customer(customer):
            print(f"✓ Customer {customer_id} updated successfully!")
            return True
        else:
            print(f"Failed to update customer {customer_id}.")
            return False
    
    def delete_customer(self, customer_id: int):
        """Delete a customer"""
        if self.db.delete_customer(customer_id):
            print(f"✓ Customer {customer_id} deleted successfully!")
            return True
        else:
            print(f"Customer with ID {customer_id} not found.")
            return False
    
    def search_customers(self, query: str):
        """Search for customers"""
        customers = self.db.search_customers(query)
        if not customers:
            print(f"No customers found matching '{query}'.")
            return
        
        print(f"\nFound {len(customers)} customer(s) matching '{query}':\n")
        print("-" * 80)
        for customer in customers:
            print(f"ID: {customer.id}")
            print(f"Name: {customer.name}")
            print(f"Email: {customer.email}")
            print(f"Phone: {customer.phone}")
            if customer.company:
                print(f"Company: {customer.company}")
            print("-" * 80)


def print_help():
    """Print help message"""
    print("""
CRM System - Customer Relationship Management

Usage: python -m crm.cli <command> [arguments]

Commands:
  add <name> <email> <phone> [company]  Add a new customer
  list                                   List all customers
  get <id>                              Get customer by ID
  update <id> [--name NAME] [--email EMAIL] [--phone PHONE] [--company COMPANY]
                                        Update customer information
  delete <id>                           Delete a customer
  search <query>                        Search customers by name, email, or company
  help                                  Show this help message

Examples:
  python -m crm.cli add "John Doe" "john@example.com" "+1234567890" "Acme Corp"
  python -m crm.cli list
  python -m crm.cli get 1
  python -m crm.cli update 1 --name "Jane Doe" --email "jane@example.com"
  python -m crm.cli delete 1
  python -m crm.cli search "Acme"
""")


def main():
    """Main entry point for CLI"""
    if len(sys.argv) < 2:
        print_help()
        sys.exit(1)
    
    command = sys.argv[1].lower()
    crm = CRM()
    
    try:
        if command == "help":
            print_help()
        
        elif command == "add":
            if len(sys.argv) < 5:
                print("Error: Missing required arguments for 'add' command.")
                print("Usage: python -m crm.cli add <name> <email> <phone> [company]")
                sys.exit(1)
            name = sys.argv[2]
            email = sys.argv[3]
            phone = sys.argv[4]
            company = sys.argv[5] if len(sys.argv) > 5 else None
            crm.add_customer(name, email, phone, company)
        
        elif command == "list":
            crm.list_customers()
        
        elif command == "get":
            if len(sys.argv) < 3:
                print("Error: Missing customer ID.")
                print("Usage: python -m crm.cli get <id>")
                sys.exit(1)
            customer_id = int(sys.argv[2])
            crm.get_customer(customer_id)
        
        elif command == "update":
            if len(sys.argv) < 3:
                print("Error: Missing customer ID.")
                print("Usage: python -m crm.cli update <id> [--name NAME] [--email EMAIL] [--phone PHONE] [--company COMPANY]")
                sys.exit(1)
            customer_id = int(sys.argv[2])
            
            # Parse optional arguments
            name = None
            email = None
            phone = None
            company = None
            
            i = 3
            while i < len(sys.argv):
                if sys.argv[i] == "--name" and i + 1 < len(sys.argv):
                    name = sys.argv[i + 1]
                    i += 2
                elif sys.argv[i] == "--email" and i + 1 < len(sys.argv):
                    email = sys.argv[i + 1]
                    i += 2
                elif sys.argv[i] == "--phone" and i + 1 < len(sys.argv):
                    phone = sys.argv[i + 1]
                    i += 2
                elif sys.argv[i] == "--company" and i + 1 < len(sys.argv):
                    company = sys.argv[i + 1]
                    i += 2
                else:
                    i += 1
            
            crm.update_customer(customer_id, name, email, phone, company)
        
        elif command == "delete":
            if len(sys.argv) < 3:
                print("Error: Missing customer ID.")
                print("Usage: python -m crm.cli delete <id>")
                sys.exit(1)
            customer_id = int(sys.argv[2])
            crm.delete_customer(customer_id)
        
        elif command == "search":
            if len(sys.argv) < 3:
                print("Error: Missing search query.")
                print("Usage: python -m crm.cli search <query>")
                sys.exit(1)
            query = sys.argv[2]
            crm.search_customers(query)
        
        else:
            print(f"Error: Unknown command '{command}'")
            print_help()
            sys.exit(1)
    
    except Exception as e:
        print(f"Error: {e}")
        sys.exit(1)
    finally:
        crm.close()


if __name__ == "__main__":
    main()
