# **Personal Project**

### To-Do list

To-Do list application keeps track of things that the user insert and cross things off when 
the user finishes the work. Everyone has chances to use it. I am interested in this project 
because I always write down all the tasks that I need to finish. I think this project is useful.

### User Stories
- As a user, I want to able to add a task to my to-do list
- As a user, I want to able to delete a task to my to-do list
- As a user, I want to able to see how many complete tasks  on my to-do list
- As a user, I want to able to see how many incomplete tasks  on my to-do list
- As a user, I want to able to see a list of complete tasks
- As a user, I want to able to see a list of incomplete tasks

- As a user, I want to able to save my to-do list file
- As a user, I want to able to be able to load my to-do list from file

- As a user, I want to query my to-do list
- As a user, I want to update my date on my to-do list
- As a user, I want to flush my to-do list

Type Hierarchy
- In the UI package, TaskFrame class extends JFrame class, which means JFrame is a superclass of TaskFrame.
Since we want to create a frame in our window, we need to invoke some methods (setSize(), setVisible()...) 
in its superclass. We also override method in Window (java.awt), such that we can indicate if the window is currently 
opaque.
- In the UI package, TaskDaoImpL implements TaskDao interface and TaskServiceImpl implements TaskService interface,
since they have similarities among unrelated classes, we use interface to achieve multiple inheritance and loose
coupling.

-In my UML class diagram, I kind of duplicate Task class, one is in the model package, another one is in the ui
package. They have some similarities (private String name...), except that the Task class in the ui package has method
about comparing different objects. Besides, maybe I should use abstract class to take generalization in other classes. 
