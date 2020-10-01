const mongoose = require('mongoose');

const DataSchema = mongoose.Schema({
    id : {
        type : String,
        required : true
    },
    name : {
        type : String,
        required : true
    }
});

const Data = module.exports = mongoose.model('Data', DataSchema);