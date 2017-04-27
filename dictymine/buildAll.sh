!#/bin/bash
set -xe

cd dbmodel && ant clean build-db
cd ../integrate \
    && ant -Dsource=discoideum-ax4 \
    && sleep 15 \
    && ant -Dsource=fasciculatum-sh3 \
    && ant -Dsource=purpureum-qsdp1 \
    && ant -Dsource=pallidum-ck8 \
    && ant -Dsource=pallidum-pn500 \
    && sleep 5
ant -Dsource=organism
cd ../postprocess && ant 
cd ../webapp && ant clean build-db-userprofile default
