const mongoose = require('mongoose');
const moment = require('moment');

const DataSchema = mongoose.Schema({
    id : {
        type : String,
        required : true
    },
    logger_id : {
        type : String,
        required : true
    },
    value : {
        type : String, 
        required : false
    },
    entry_time : {
        type : Date,
        default : moment.utc().toDate(Date.now()) ,
        required : true
    }
});

const Data = module.exports = mongoose.model('Data', DataSchema);