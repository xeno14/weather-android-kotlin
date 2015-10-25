package io.dp.weather.app.fragment

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import butterknife.bindView
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import io.dp.weather.app.BusSubcomponent
import io.dp.weather.app.R
import io.dp.weather.app.SchedulersManager
import io.dp.weather.app.activity.SettingsActivity
import io.dp.weather.app.activity.debug.DebugActivity
import io.dp.weather.app.adapter.PlacesAdapter
import io.dp.weather.app.adapter.PlacesAutoCompleteAdapter
import io.dp.weather.app.db.DatabaseHelper
import io.dp.weather.app.db.OrmliteCursorLoader
import io.dp.weather.app.db.Queries
import io.dp.weather.app.db.table.Place
import io.dp.weather.app.event.AddPlaceEvent
import io.dp.weather.app.event.DeletePlaceEvent
import io.dp.weather.app.event.UpdateListEvent
import io.dp.weather.app.utils.Observables
import rx.Subscription
import java.sql.SQLException
import java.util.*
import javax.inject.Inject

public class WeatherFragment : BaseFragment(), LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    var subscriptionList: MutableList<Subscription> = ArrayList()

    @Inject lateinit var geocoder: Geocoder
    @Inject lateinit var adapter: PlacesAdapter
    @Inject lateinit var dbHelper: DatabaseHelper
    @Inject lateinit var bus: Bus
    @Inject lateinit var placesAutoCompleteAdapter: PlacesAutoCompleteAdapter
    @Inject lateinit var schedulersManager: SchedulersManager

    val recyclerView: RecyclerView by bindView(R.id.recycler)
    val swipeRefreshView: SwipeRefreshLayout by bindView(R.id.swipe_layout)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshView.setOnRefreshListener(this)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (component as BusSubcomponent).inject(this)

        retainInstance = true

        adapter.preparedQuery = Queries.prepareCityQuery(dbHelper)
        recyclerView.adapter = adapter

        loaderManager.restartLoader(0, null, this)

        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        bus.register(this)

        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        bus.unregister(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater!!.inflate(R.menu.main, menu)

        val addItem = menu!!.findItem(R.id.action_add)

        val addView = addItem.actionView as AutoCompleteTextView
        MenuItemCompat.setOnActionExpandListener(addItem, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                addView.post {
                    addView.requestFocus()
                    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(addView, InputMethodManager.SHOW_IMPLICIT)
                }
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when (id) {
            R.id.action_add -> return true

            R.id.action_settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }

            R.id.action_debug -> {
                startActivity(Intent(activity, DebugActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        for (s in subscriptionList) {
            s.unsubscribe()
        }
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<Cursor>? {
        try {
            return OrmliteCursorLoader(activity, dbHelper.getPlaceDao(), adapter.preparedQuery)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        adapter.changeCursor(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter.changeCursor(null)
    }

    override fun onRefresh() {
        swipeRefreshView.isRefreshing = true
        adapter.clear()
        adapter.notifyDataSetChanged()
        swipeRefreshView.isRefreshing = false
    }

    @Subscribe public fun onUpdateList(event: UpdateListEvent) {
        loaderManager.restartLoader(0, null, this)
    }

    @Subscribe public fun onDeletePlace(event: DeletePlaceEvent) {
        if (event.id != null) {
            try {
                dbHelper.getPlaceDao()!!.deleteById(event.id)
                loaderManager.restartLoader(0, null, this)
            } catch (e: SQLException) {
                e.printStackTrace()
            }

        }
    }

    @Subscribe public fun onAddPlace(event: AddPlaceEvent) {
        val s = Observables.getGeoForPlace(activity, dbHelper, geocoder, event.lookupPlace)
                .compose(schedulersManager.applySchedulers<Place>())
                .subscribe({
                    bus.post(UpdateListEvent())
                }, {})

        subscriptionList.add(s)
    }

    companion object {

        public fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }
}
