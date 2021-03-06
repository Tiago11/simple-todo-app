# Pre-work - *Todo App*

**Todo App** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Santiago Paez**

Time spent: **8** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file - Persistance done using DBFlow and a `SimpleCursorAdapter` as a first approach. I'll work next on using a `CursorLoader` through the `LoaderManager` to improve responsiveness.
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item) - due dates on both add and edit actions.
* [X] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:


* [X] Add splash screen and launch icon.
* [X] Reorgnize files under java folder into packages (activities, adapters, data, models, etc)
* [X] The item shows "Today" or "Tomorrow" if the due date is one of those.
* [X] Add AlertDialog to warn the user before deleting an item from the list.
* [X] Add new Todo Item as its own Activity, with the `MainActivity` as the parent activity and "Up" button to facilitate navigation.
* [X] Use Parcel (Parceler) to send java objects through activities.
* [X] Add Toolbar with actions using `android.support.v7.widget.Toolbar`.
* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

![](http://i.imgur.com/Pf8oDkd.gif)

[Link to video on Imgur](http://imgur.com/Pf8oDkd)

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** 
The Android app development platform is solid. I have to confess that I was expecting way more issues while setting it up and working on my first app, just because that's usually what has happened to me with other platforms.
In terms of layout, I've used bootstrap(HTML/CSS) and XUL while doing web development. Having a drag and drop environment that communicates two-ways with XML is sooooo nice.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** The `ArrayAdapter` was a surprise, a good surprise. It feels like it's Android's way of implementing a "separation of concerns" of sorts between the data and the way in which that data is going to be presented on the screen of a smartphone. It takes away a big part of the heavy lifting that involves transforming the data in each direction.
The `convertView` parameter give us the chance to reuse a row of the `listView`. It's part of the heavy lifting that the adapter does for us, instead of creating a new row for every item of our list (which could be very large), it keeps a row for what the user can see on the screen at a given moment. The `getView` method can ve overridden to change the layout of the `listView` items.

## Notes

The main challenge I faced was to find quality documentation. It seems that the best documentation out there is the official Android documentation and Codepath Android Cliffnotes. There is a lot of personal blogs with examples, but they are either out of date or using strange practices.

Another difficulty I found, it seems to me like there is an "Android way" of doing certain things. It wasn't always clear what was the best approach to solve a specific problem (of the many possible options) and I couldn't find the answers online. Knowing people who develops professionally might be a good way to evacuate those doubts. 
 

## License

    Copyright [2017] [Santiago Paez]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
