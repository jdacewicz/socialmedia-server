package pl.jdacewicz.socialmediaserver.datagrouper;

interface DataGrouperServiceFactory {

    DataGrouperService<? extends Group> getDataGrouperService(String type);
}
