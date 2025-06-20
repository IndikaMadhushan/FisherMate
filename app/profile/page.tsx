"use client"

import type React from "react"

import { useState, useEffect } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Textarea } from "@/components/ui/textarea"
import { User } from "lucide-react"
import { useRouter } from "next/navigation"

export default function ProfilePage() {
  const [formData, setFormData] = useState({
    name: "",
    experience: "",
    boatType: "",
    boatLength: "",
    crewSize: "",
    licenseNumber: "",
    emergencyContact: "",
    homePort: "",
    specialization: "",
  })
  const router = useRouter()

  useEffect(() => {
    // Check if user is logged in
    const isLoggedIn = localStorage.getItem("fisherman_logged_in")
    if (!isLoggedIn) {
      router.push("/login")
      return
    }

    // Load existing profile data
    const savedProfile = localStorage.getItem("fisherman_profile")
    const userName = localStorage.getItem("fisherman_name")
    if (savedProfile) {
      setFormData(JSON.parse(savedProfile))
    } else if (userName) {
      setFormData((prev) => ({ ...prev, name: userName }))
    }
  }, [router])

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    localStorage.setItem("fisherman_profile", JSON.stringify(formData))
    router.push("/dashboard")
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-cyan-100 p-4">
      <div className="max-w-2xl mx-auto">
        <Card>
          <CardHeader className="text-center">
            <div className="flex justify-center mb-4">
              <div className="p-3 bg-blue-100 rounded-full">
                <User className="h-8 w-8 text-blue-600" />
              </div>
            </div>
            <CardTitle className="text-2xl font-bold">Fisherman Profile</CardTitle>
            <CardDescription>Complete your profile to get personalized trip recommendations</CardDescription>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="name">Full Name</Label>
                  <Input
                    id="name"
                    value={formData.name}
                    onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                    required
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="experience">Years of Experience</Label>
                  <Select
                    value={formData.experience}
                    onValueChange={(value) => setFormData({ ...formData, experience: value })}
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="Select experience" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="beginner">0-2 years</SelectItem>
                      <SelectItem value="intermediate">3-10 years</SelectItem>
                      <SelectItem value="experienced">11-20 years</SelectItem>
                      <SelectItem value="expert">20+ years</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="boatType">Boat Type</Label>
                  <Select
                    value={formData.boatType}
                    onValueChange={(value) => setFormData({ ...formData, boatType: value })}
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="Select boat type" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="small-boat">Small Boat ({"<"} 20ft)</SelectItem>
                      <SelectItem value="medium-boat">Medium Boat (20-40ft)</SelectItem>
                      <SelectItem value="large-boat">Large Boat ({">"} 40ft)</SelectItem>
                      <SelectItem value="trawler">Trawler</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
                <div className="space-y-2">
                  <Label htmlFor="boatLength">Boat Length (feet)</Label>
                  <Input
                    id="boatLength"
                    type="number"
                    placeholder="25"
                    value={formData.boatLength}
                    onChange={(e) => setFormData({ ...formData, boatLength: e.target.value })}
                  />
                </div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="crewSize">Typical Crew Size</Label>
                  <Input
                    id="crewSize"
                    type="number"
                    placeholder="3"
                    value={formData.crewSize}
                    onChange={(e) => setFormData({ ...formData, crewSize: e.target.value })}
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="licenseNumber">Fishing License Number</Label>
                  <Input
                    id="licenseNumber"
                    placeholder="FL-123456"
                    value={formData.licenseNumber}
                    onChange={(e) => setFormData({ ...formData, licenseNumber: e.target.value })}
                  />
                </div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="homePort">Home Port</Label>
                  <Input
                    id="homePort"
                    placeholder="Miami Harbor"
                    value={formData.homePort}
                    onChange={(e) => setFormData({ ...formData, homePort: e.target.value })}
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="emergencyContact">Emergency Contact</Label>
                  <Input
                    id="emergencyContact"
                    placeholder="+1-555-0123"
                    value={formData.emergencyContact}
                    onChange={(e) => setFormData({ ...formData, emergencyContact: e.target.value })}
                  />
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="specialization">Fishing Specialization</Label>
                <Textarea
                  id="specialization"
                  placeholder="Deep sea fishing, coastal fishing, specific fish types..."
                  value={formData.specialization}
                  onChange={(e) => setFormData({ ...formData, specialization: e.target.value })}
                />
              </div>

              <Button type="submit" className="w-full bg-blue-600 hover:bg-blue-700">
                Save Profile & Continue
              </Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
