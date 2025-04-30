import React from "react";
import {
    useNavigate,
} from "react-router-dom";
// import { Button } from "@/components/ui/button";
// import { Card, CardContent } from "@/components/ui/card";
// import { Input } from "@/components/ui/input";
// import { Label } from "@/components/ui/label";
import { Button, Menu, MenuItem, Typography, Card, TextField, InputLabel } from "@mui/material";

const AddMachine = () => {
    const navigate = useNavigate();
  
    const handleSubmit = (e: React.FormEvent) => {
      e.preventDefault();
      // Normally you'd handle actual submission logic here
      navigate("/");
    };
  
    return (
      <div className="min-h-screen bg-gray-50 p-4">
        <h1 className="text-2xl font-semibold mb-6">Add New Machine</h1>
        <Card className="p-6 max-w-lg mx-auto">
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <InputLabel htmlFor="name">Name</InputLabel>
              <TextField id="name" required />
            </div>
            <div>
              <InputLabel htmlFor="manufacturer">Manufacturer</InputLabel>
              <TextField id="manufacturer" required />
            </div>
            <div>
              <InputLabel htmlFor="barcode">Barcode</InputLabel>
              <TextField id="barcode" required />
            </div>
            <Button type="submit" className="w-full">
              Submit
            </Button>
          </form>
        </Card>
      </div>
    );
  };

  export default AddMachine
  