package com.wa82bj.ads_mvvm_github.ui.detail

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.wa82bj.ads_mvvm_github.R
import com.wa82bj.ads_mvvm_github.databinding.ActivityAdsDetailBinding
import com.wa82bj.ads_mvvm_github.util.*
import com.wa82bj.ads_mvvm_github.util.ext.loadImageFull
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    private val TAG = DetailActivity::class.java.name
    private lateinit var binding: ActivityAdsDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    @SuppressLint("LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ads_detail)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        binding.detailModel = viewModel

        val adssId = intent?.extras?.getString(KEY_ADS_ID)

        if (adssId.equals(null)){
            getOffer()
        }else {
            getAds()
        }

        isFavorite()

        binding.callFab.setOnClickListener {

            if (!checkPhonePermission()){
                onSNACK(it,"You can't make phone call without accept phone permission!"
                    ,"Ok")
            }else{
                callPhone()
            }
        }

        binding.mapFab.setOnClickListener {view ->

            goToAdsLocation()
        }

    }

    private fun isFavorite() {
        viewModel.isFavorite.observe(this, Observer {
            it?.let {

                if (it == 0){
                    binding.lottieLike.visibility = GONE
                    binding.detailFab.setImageResource(

                        R.drawable.ic_favorite_black_24dp
                    )

                }else
                    binding.lottieLike.visibility = VISIBLE
                binding.detailFab.setImageResource(

                    R.drawable.ic_favorit
                )
            }
        })

        binding.detailFab.setOnClickListener {view ->

            viewModel.productData.observe(this, Observer {

                if (it.fav == true){
                    onSNACK(view,"Removed from Favorite List!"
                        ,"Ok")
                    viewModel.updateAdsFavoriteStatus()
                    binding.detailFab.setImageResource(

                        R.drawable.ic_star_white_24dp)

                }else{
                    onSNACK(view,"Added to Favorite List!"
                        ,"Ok")
                    viewModel.updateAdsFavoriteStatus()
                    binding.detailFab.setImageResource(

                        R.drawable.ic_star_full_vector
                    )
                }
            })

            viewModel.offerData.observe(this, Observer {

                if (it.fav == true){
                    onSNACK(view,"Removed from Favorite List!"
                        ,"Ok")
                    viewModel.updateOfferFavoriteStatus()
                    binding.detailFab.setImageResource(

                        R.drawable.ic_star_white_24dp)

                }else{
                    onSNACK(view,"Added to Favorite List!"
                        ,"Ok")
                    viewModel.updateOfferFavoriteStatus()
                    binding.detailFab.setImageResource(

                        R.drawable.ic_star_full_vector
                    )
                }
            })

        }

    }
    private fun getAds() {
        
        val adsId = intent?.extras?.getString(KEY_ADS_ID) ?: return
        viewModel.getAdsDetail(adsId)

        viewModel.productData.observe(this, Observer {
            binding.detailTitleTextView.text = it?.description
            binding.productName.text = it?.title
            binding.productDate.text = it?.dateCreated
            binding.adsCate.text = it?.category + " / " +it.subCate
            binding.adsCity.text = it?.city
            binding.detailToolbarImageView.loadImageFull(it?.image)

            if (it.fav == true){
                viewModel.checkAdsFavoriteStatus(ADD_TO_FAVORITE , adsId)
            }else{
                viewModel.checkAdsFavoriteStatus(REMOVE_FROM_FAVORITE , adsId)
            }
        })

    }

    private fun getOffer() {
        val offerId = intent?.extras?.getString(KEY_OFFER_ID) ?: return
        viewModel.getOfferDetail(offerId)

        viewModel.offerData.observe(this, Observer {
            binding.detailTitleTextView.text = it?.description
            binding.productName.text = it?.title
            binding.productDate.text = it?.dateCreated
            binding.adsCate.text = it?.category + " / " +it.subCate
            binding.adsCity.text = it?.city
            binding.detailToolbarImageView.loadImageFull(it?.image)

            if (it.fav == true){
                viewModel.checkOfferFavoriteStatus(ADD_TO_FAVORITE , offerId)
            }else{
                viewModel.checkOfferFavoriteStatus(REMOVE_FROM_FAVORITE , offerId)
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val KEY_ADS_ID = "KEY_ADS_ID"
        private val KEY_OFFER_ID = "KEY_OFFER_ID"
        private val REQUEST_CODE = 42
        private val ADD_TO_FAVORITE = 1
        private val REMOVE_FROM_FAVORITE = 0
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE ) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                callPhone()
            }
            return
        }
    }

    fun callPhone(){
        viewModel.productData.observe(this, Observer {
            callPhoneIntent(it.condition)

        })

        viewModel.offerData.observe(this, Observer {
            callPhoneIntent(it.condition)

        })
    }

    fun goToAdsLocation(){
        viewModel.offerData.observe(this, Observer {
            goToLocation(it.latitude,it.longitude)
        })

        viewModel.productData.observe(this, Observer {
            goToLocation(it.latitude,it.longitude)
        })
    }


}