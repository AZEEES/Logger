const mongoose = require('mongoose');

const DataSchema = mongoose.Schema({
    id : {
        type : String,
        required : true
    },
    name : {
        type : String,
        required : true
    },
    entry_time : {
        type : Date,
        default : Date.now(),
        required : true
    }
});

const Data = module.exports = mongoose.model('Data', DataSchema);