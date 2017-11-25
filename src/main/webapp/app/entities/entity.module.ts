import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WegoRideModule } from './ride/ride.module';
import { WegoRideOfferModule } from './ride-offer/ride-offer.module';
import { WegoLocationModule } from './location/location.module';
import { WegoRideRequestModule } from './ride-request/ride-request.module';
import { WegoVehcileModule } from './vehcile/vehcile.module';
import { WegoMessageModule } from './message/message.module';
import { WegoRatingModule } from './rating/rating.module';
import { WegoBookingModule } from './booking/booking.module';
import { WegoDriverModule } from './driver/driver.module';
import { WegoPassengerModule } from './passenger/passenger.module';
import { WegoCountryModule } from './country/country.module';
import { WegoCityModule } from './city/city.module';
import { WegoTermsModule } from './terms/terms.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        WegoRideModule,
        WegoRideOfferModule,
        WegoLocationModule,
        WegoRideRequestModule,
        WegoVehcileModule,
        WegoMessageModule,
        WegoRatingModule,
        WegoBookingModule,
        WegoDriverModule,
        WegoPassengerModule,
        WegoCountryModule,
        WegoCityModule,
        WegoTermsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WegoEntityModule {}
