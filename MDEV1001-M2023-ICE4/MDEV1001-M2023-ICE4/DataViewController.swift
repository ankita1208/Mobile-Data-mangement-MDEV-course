

import UIKit
import CoreData
class DataViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var tableView: UITableView!
       var movies: [Movie] = []
       
       override func viewDidLoad() {
           super.viewDidLoad()
           fetchData()
       }
       
       func fetchData() {
               guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
                   return
               }
               
               let context = appDelegate.persistentContainer.viewContext
               
               let fetchRequest: NSFetchRequest<Movie> = Movie.fetchRequest()
               
               do {
                   movies = try context.fetch(fetchRequest)
                   tableView.reloadData()
               } catch {
                   print("Failed to fetch data: \(error)")
               }
           }
       
       func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
               return movies.count
           }
           
           func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
               let cell = tableView.dequeueReusableCell(withIdentifier: "MovieCell", for: indexPath)
               
               let movie = movies[indexPath.row]
               
               cell.textLabel?.text = movie.title
               cell.detailTextLabel?.text = movie.studio
               
               return cell
           }

}

