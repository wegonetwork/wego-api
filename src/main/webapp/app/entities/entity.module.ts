import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterRideModule } from './ride/ride.module';
import { JhipsterRideOfferModule } from './ride-offer/ride-offer.module';
import { JhipsterRideRequestModule } from './ride-request/ride-request.module';
import { JhipsterVehcileModule } from './vehcile/vehcile.module';
import { JhipsterMessageModule } from './message/message.module';
import { JhipsterRatingModule } from './rating/rating.module';
import { JhipsterBookingModule } from './booking/booking.module';
import { JhipsterDriverModule } from './driver/driver.module';
import { JhipsterPassengerModule } from './passenger/passenger.module';
import { JhipsterCountryModule } from './country/country.module';
import { JhipsterCityModule } from './city/city.module';
import { JhipsterEthAccountModule } from './eth-account/eth-account.module';
import { JhipsterLocationModule } from './location/location.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterRideModule,
        JhipsterRideOfferModule,
        JhipsterRideRequestModule,
        JhipsterVehcileModule,
        JhipsterMessageModule,
        JhipsterRatingModule,
        JhipsterBookingModule,
        JhipsterDriverModule,
        JhipsterPassengerModule,
        JhipsterCountryModule,
        JhipsterCityModule,
        JhipsterEthAccountModule,
        JhipsterLocationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterEntityModule {}
